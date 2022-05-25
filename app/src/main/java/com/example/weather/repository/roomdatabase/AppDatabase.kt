package com.example.weather.repository.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.repository.account.room.AccountDbEntity
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.repository.city.room.CitiesListDao

// todo #8: Create a database class by extending RoomDatabase.
//          - annotate this class with @Database annotation
//          - use 'version' parameter to specify database scheme version (should be version=1)
//          - use 'entities' parameter to list all entities (classes annotated with @Entity)
//          - use 'views' parameter to list all views (classes annotated with @DatabaseView)

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    // todo #10: Add abstract getAccountsDao() method

    // todo #18: Add abstract getBoxesDao() method

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getCitiesListDao(): CitiesListDao

}