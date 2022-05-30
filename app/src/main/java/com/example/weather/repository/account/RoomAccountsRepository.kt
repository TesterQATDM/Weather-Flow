package com.example.weather.repository.account

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.weather.repository.account.room.AccountDbEntity
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.room.entities.Field
import com.example.weather.repository.account.room.entities.SignUpData
import com.example.weather.repository.settings.AppSettings
import com.example.weather.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RoomAccountsRepository(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings
): AccountRepository {

    private val currentAccountFlow = MutableStateFlow<Account?>(null)

    private val currentAccountIdFlowRoom = AsyncLoader {
        MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))
    }

    private val accounts = mutableListOf(
        AccountRecord(
        Account(
            id = 100,
            username = "123",
            email = "123"
        ),
        password = "123"
        )
    )

    init {
        currentAccountFlow.value = accounts[0].account
    }

    override fun getAccount(): Flow<Account?> = currentAccountFlow

    override suspend fun isSignedIn(): Boolean {
        delay(100)
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldsExceptions(Field.Email)
        if (password.isBlank()) throw EmptyFieldsExceptions(Field.Password)
        val tuple = accountsDao.findAccountByEmail(email) ?: throw AuthException()
        if (tuple.password != password) throw AuthException()
        appSettings.setCurrentAccountId(tuple.id)
        currentAccountIdFlowRoom.get().value = AccountId(tuple.id)
        Log.d("log", tuple.id.toString())
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

    override suspend fun changeName(name: String) {
        if (name.isBlank()) throw EmptyFieldsExceptions(Field.Username)
        delay(100)
        val currentAccount = currentAccountFlow.value ?: throw AuthException()
        val updatedAccount = currentAccount.copy(username = name)
        currentAccountFlow.value = updatedAccount
        val currentRecord = accounts.firstOrNull { it.account.email == currentAccount.email } ?: throw AuthException()
        currentRecord.account = updatedAccount
    }

    override suspend fun logout(){
        currentAccountFlow.value = null
    }

    private class AccountRecord(
        var account: Account,
        val password: String
    )

    private class AccountId(val value: Long)
}