package com.example.weather.repository.account.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.room.entities.SignUpData

// todo #5: Define AccountDbEntity.
//          - fields: id, email, username, password.
//          - annotate class with @Entity annotation:
//            - use 'indices' parameter to add UNIQUE constraint for 'email' field;
//            - use 'tableName' to customize the table name for this entity;
//          - use @PrimaryKey annotation for the identifier field:
//            - use 'autoGenerate' parameter if you don't want to specify ID every time upon creating a new account;
//          - use @ColumnInfo(name='...') to customize column names and add additional
//            parameters (e.g. 'collate = ColumnInfo.NOCASE').
//          - add toAccount() method for mapping AccountDbEntity objects to Account objects.
//          - add companion object with fromSignUpData(signUpData: SignUpData) method for creating a new
//            instance of AccountDbEntity from SignUpData object.

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)

data class AccountDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String
){

    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData) = AccountDbEntity(
            id = 0, // SQLite generates identifier automatically if ID = 0
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password
        )
    }
}