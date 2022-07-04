package com.example.weather.repository.account.room.entities

data class Account(
    val id: Long,
    val username: String,
    val createdAt: Long = UNKNOWN_CREATED_AT
) {
    companion object {
        const val UNKNOWN_CREATED_AT = 0L
    }
}