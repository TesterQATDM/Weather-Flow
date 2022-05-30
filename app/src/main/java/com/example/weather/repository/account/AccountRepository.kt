package com.example.weather.repository.account

import com.example.weather.repository.Repository
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.room.entities.SignUpData
import kotlinx.coroutines.flow.Flow
import com.example.weather.utils.*

/**
 * Repository with account-related actions, e.g. sign-in, sign-up, edit account etc.
 */

interface AccountRepository: Repository {

    /**
     * Whether user is signed-in or not.
     */
    suspend fun isSignedIn(): Boolean

    /**
     * Get the account info of the current signed-in user.
     */
    fun getAccount(): Flow<Account?>

    /**
     * Try to sign-in with the email and password.
     * @throws [EmptyFieldsExceptions]
     * @throws [AuthException]
     * @throws [AccountIsExistException]
     */
    suspend fun signIn(email: String, password: String)

    /**
     * Create a new account.
     * @throws [EmptyFieldsExceptions]
     * @throws [PasswordIsMismatchExceptions]
     * @throws [AccountIsExistException]
     * @throws [StorageException]
     */
    suspend fun createAccountRepository(signUpData: SignUpData)

    /**
     * Change the username of the current signed-in user.
     * @throws [EmptyFieldsExceptions]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun changeName(name: String)

    /**
     * Sign-out from the app.
     */
    suspend fun logout()

}