package com.example.nikitalevcenko.vk.modules.auth.vm

interface IAuthViewModel {
    abstract fun onAccessTokenReceived(accessToken: String, userId: Long)
}