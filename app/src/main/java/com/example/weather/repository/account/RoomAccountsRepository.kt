package com.example.weather.repository.account

import android.database.sqlite.SQLiteConstraintException
import com.example.weather.repository.account.room.AccountDbEntity
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.room.entities.AccountUpdateUsernameTuple
import com.example.weather.repository.account.room.entities.Field
import com.example.weather.repository.account.room.entities.SignUpData
import com.example.weather.repository.settings.AppSettings
import com.example.weather.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class RoomAccountsRepository(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings
): AccountRepository {

    private val currentAccountIdFlowRoom = AsyncLoader {
        MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))
    }

    override suspend fun getAccount(): Flow<Account?> {
        return currentAccountIdFlowRoom.get()
            .flatMapLatest { accountId ->
                if (accountId.value == AppSettings.NO_ACCOUNT_ID) {
                    flowOf(null)
                } else {
                    accountsDao.getById(accountId.value).map { it?.toAccount()}
                }
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun isSignedIn(): Boolean {
        delay(2000)
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldsExceptions(Field.Email)
        if (password.isBlank()) throw EmptyFieldsExceptions(Field.Password)
        val tuple = accountsDao.findAccountByEmail(email) ?: throw AuthException()
        if (tuple.password != password) throw AuthException()
        appSettings.setCurrentAccountId(tuple.id)
        currentAccountIdFlowRoom.get().value = AccountId(tuple.id)
    }

    override suspend fun createAccountRepository(signUpData: SignUpData) = wrapSQLiteException(Dispatchers.IO){
        signUpData.validate()
        try {
            val entity = AccountDbEntity.fromSignUpData(signUpData)
            accountsDao.createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            val appException = AccountIsExistException()
            appException.initCause(e)
            throw appException
        }
    }

    override suspend fun changeName(name: String)  = wrapSQLiteException(Dispatchers.IO){
        if (name.isBlank()) throw EmptyFieldsExceptions(Field.Username)
        delay(100)
        val accountId = appSettings.getCurrentAccountId()
        if (accountId == AppSettings.NO_ACCOUNT_ID) throw AuthException()
        val tempAccount = AccountUpdateUsernameTuple(accountId, name)
        accountsDao.updateUsername(tempAccount)

        currentAccountIdFlowRoom.get().value = AccountId(accountId)

        return@wrapSQLiteException
    }

    override suspend fun logout(){
        currentAccountIdFlowRoom.get().value = AccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlowRoom.get().value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    private class AccountId(val value: Long)
}