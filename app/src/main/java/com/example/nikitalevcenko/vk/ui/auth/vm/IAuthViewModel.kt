package com.example.nikitalevcenko.vk.ui.auth.vm

interface IAuthViewModel {
    abstract fun onAccessTokenReceived(accessToken: String, userId: Long)
}