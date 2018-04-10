package com.example.nikitalevcenko.vk.settings

interface SettingsStore {
    fun saveAccessToken(accessToken: String)
    fun saveUserId(userId: Long)

    fun accessToken(): String
    fun hasAccessToken(): Boolean
    fun userId(): Long
}