package com.example.nikitalevcenko.vk.repo

import android.arch.lifecycle.MutableLiveData
import com.example.nikitalevcenko.vk.settings.SettingsStore

class AuthRepo(private val settingsStore: SettingsStore) : IAuthRepo {
    override fun auth(accessToken: String, userId: Long) {
        settingsStore.saveAccessToken(accessToken)
        settingsStore.saveUserId(userId)
    }

    override fun isAuthorized() = MutableLiveData<Boolean>().apply {
        value = settingsStore.hasAccessToken()
    }

}