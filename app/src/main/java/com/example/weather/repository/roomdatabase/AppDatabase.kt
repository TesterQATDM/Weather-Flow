package com.example.weather.repository.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.repository.account.room.AccountDbEntity
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.repository.city.room.CitiesListDao
import com.example.weather.repository.city.room.CitiesListDbEntity

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        CitiesListDbEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getCitiesListDao(): CitiesListDao

    companion object {
        private var  dbINSTANCE: AppDatabase? = null

        fun getDB(context: Context): AppDatabase{
            if (dbINSTANCE == null){
                dbINSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext, AppDatabase::class.java, "MYDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }

}