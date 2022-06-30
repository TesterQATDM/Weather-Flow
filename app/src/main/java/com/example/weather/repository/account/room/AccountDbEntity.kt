package com.example.weather.repository.account.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.room.entities.SignUpData

@Entity(tableName = "accounts")

data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
){

    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData) = AccountDbEntity(
            id = 0, // SQLite generates identifier automatically if ID = 0
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password,
            createdAt = System.currentTimeMillis()
        )
    }
}
