package com.example.nikitalevcenko.vk.settings

import android.content.Context

private const val APP_PREFERENCES = "VK_PREFERENCES"
private const val TOKEN = "TOKEN"
private const val USER_ID = "USER_ID"

class SharedPreferencesSettings(context: Context) : SettingsStore {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit()
                .putString(TOKEN, accessToken)
                .apply()

    }

    override fun saveUserId(userId: Long) {
        sharedPreferences.edit()
                .putLong(USER_ID, userId)
                .apply()
    }

    override fun accessToken() = sharedPreferences.getString(TOKEN, "")

    override fun hasAccessToken() = sharedPreferences.contains(TOKEN)

    override fun userId() = sharedPreferences.getLong(USER_ID, 0)
}