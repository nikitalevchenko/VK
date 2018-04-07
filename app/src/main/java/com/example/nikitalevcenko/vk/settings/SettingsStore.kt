package com.example.nikitalevcenko.vk.settings

interface SettingsStore {
    fun saveAccessToken(accessToken: String)
    fun getAccessToken(): String
    fun hasAccessToken(): Boolean

    fun saveUserId(userId: Long)
    fun getUserId(): Long
}