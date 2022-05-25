package com.example.weather.repository.account.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weather.repository.account.room.entities.AccountSignInTuple
import com.example.weather.repository.account.room.entities.AccountUpdateUsernameTuple
import kotlinx.coroutines.flow.Flow

// todo #9: Create a DAO interface for querying data from 'accounts' table, updating username and
//          creating a new account.
//          - annotate it with @Dao annotation.
//          - add method for querying ID and Password by email:
//            - annotate the method with @Query
//            - write a SQL-query to fetch ID and Password by Email
//            - return a tuple created before (step #6)
//          - add method for updating username:
//            - use @Update annotation and specify 'entity' parameter = AccountDbEntity::class
//            - add one argument to pass a tuple created before (step #7)
//          - add method for creating a new account
//            - use @Insert annotation
//            - add one argument: AccountDbEntity
//          - add method for querying account info by ID
//            - annotations: @Query
//            - arguments: accountId
//            - return type: Flow<AccountDbEntity>

@Dao
interface AccountsDao {

    @Query("SELECT id, password FROM accounts WHERE email = :email")
    suspend fun findAccountByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountDbEntity::class)
    suspend fun updateUsername(account: AccountUpdateUsernameTuple)

    @Insert(entity = AccountDbEntity::class)
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getById(accountId: Long): Flow<AccountDbEntity?>

}