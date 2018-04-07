package com.example.nikitalevcenko.vk.repo

import android.arch.lifecycle.LiveData

interface IAuthRepo {
    fun auth(accessToken: String, userId: Long)
    fun isAuthorized(): LiveData<Boolean>
}