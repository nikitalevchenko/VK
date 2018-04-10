package com.example.nikitalevcenko.vk.ui

import com.example.nikitalevcenko.vk.exceptions.AccessTokenExpiredException
import com.example.nikitalevcenko.vk.router.AUTH_MODULE
import ru.terrakok.cicerone.Router

interface ViewModelWithRouter {
    val router: Router

    fun handleError(throwable: Throwable?) {
        if (throwable == null) return

        when (throwable::class) {
            AccessTokenExpiredException::class -> router.newRootScreen(AUTH_MODULE)
            else -> throwable.printStackTrace()
        }
    }
}