package com.example.weather.repository.settings

import android.content.Context
import com.example.weather.repository.settings.AppSettings.Companion.NO_ACCOUNT_ID

class SharedPreferencesAppSettings(
    appContext: Context
): AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun getCurrentAccountId(): Long = sharedPreferences.getLong(PREF_CURRENT_ACCOUNT_ID, NO_ACCOUNT_ID)


    override fun setCurrentAccountId(accountId: Long) {
        sharedPreferences.edit()
            .putLong(PREF_CURRENT_ACCOUNT_ID, accountId)
            .apply()
    }

    companion object {
        private const val PREF_CURRENT_ACCOUNT_ID = "currentAccountId"
    }
}