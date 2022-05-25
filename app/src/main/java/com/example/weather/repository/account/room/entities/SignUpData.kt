package com.example.weather.repository.account.room.entities

import com.example.weather.utils.EmptyFieldsExceptions
import com.example.weather.utils.PasswordIsMismatchExceptions

/**
 * Fields that should be provided during creating a new account.
 */
data class SignUpData(
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldsExceptions(Field.Email)
        if (username.isBlank()) throw EmptyFieldsExceptions(Field.Username)
        if (password.isBlank()) throw EmptyFieldsExceptions(Field.Password)
        if (password != repeatPassword) throw PasswordIsMismatchExceptions()
    }
}