package com.example.nikitalevcenko.vk.repo.auth

import android.arch.lifecycle.LiveData
import com.example.nikitalevcenko.vk.settings.SettingsStore

class AuthRepo(private val settingsStore: SettingsStore) : IAuthRepo {
    override fun auth(accessToken: String, userId: Long) {
        settingsStore.saveAccessToken(accessToken)
        settingsStore.saveUserId(userId)
    }

    override fun isAuthorized() = object : LiveData<Boolean>() {
        override fun onActive() {
            super.onActive()
            postValue(settingsStore.hasAccessToken())
        }
    }

}