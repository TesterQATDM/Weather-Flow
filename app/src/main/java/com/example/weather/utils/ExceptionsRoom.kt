package com.example.weather.utils

import com.example.weather.repository.account.room.entities.Field

open class AppExceptions: RuntimeException()

class EmptyFieldsExceptions(
    val field : Field
): AppExceptions()

/**
 * when Invalid email or password
 */
class AuthException: AppExceptions()

/**
 * Password is mismatch
 */
class PasswordIsMismatchExceptions: AppExceptions()

/**
 * Account Is Exist
 */

class AccountIsExistException: AppExceptions()

/**
 * Local storage error
 */
class StorageException: AppExceptions()

//if city is Exist
class CityIsExistException: AppExceptions()

