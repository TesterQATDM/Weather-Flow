package com.example.weather.repository.account

import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.room.entities.Field
import com.example.weather.repository.account.room.entities.SignUpData
import com.example.weather.utils.AccountIsExistException
import com.example.weather.utils.AuthException
import com.example.weather.utils.PasswordIsMismatchExceptions
import com.example.weather.utils.EmptyFieldsExceptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AccountInMemory: AccountRepository {

    private val currentAccountFlow = MutableStateFlow<Account?>(null)

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
        return currentAccountFlow.value !=null
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldsExceptions(Field.Email)
        if (password.isBlank()) throw EmptyFieldsExceptions(Field.Password)
        val record = accounts.firstOrNull { it.account.email == email }
        if (record != null && record.password == password)
            currentAccountFlow.value = record.account
        else
            throw AuthException()
    }

    override suspend fun createAccountRepository(
        signUpData: SignUpData
    ) {
        if (signUpData.email.isBlank()) throw EmptyFieldsExceptions(Field.Email)
        if (signUpData.username.isBlank()) throw EmptyFieldsExceptions(Field.Username)
        if (signUpData.password.isBlank()) throw EmptyFieldsExceptions(Field.Password)
        if (signUpData.repeatPassword.isBlank()) throw EmptyFieldsExceptions(Field.RepeatPassword)
        if (signUpData.password != signUpData.repeatPassword) throw PasswordIsMismatchExceptions()
        val record = accounts.firstOrNull { it.account.email == signUpData.email }
        if (record != null) throw AccountIsExistException()
        val account = AccountRecord(
            Account(
                id = 1000,
                username = signUpData.username,
                email = signUpData.email
            ),
            password = signUpData.password
        )
        accounts.add(account)
        /*record = accounts.firstOrNull { it.account.mail == email }
        currentAccountFlow.value = record?.account
        In Future will be signIn after create Account*/
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

    override fun logout(){
        currentAccountFlow.value = null
    }

    private class AccountRecord(
        var account: Account,
        val password: String
    )
}