package com.example.nikitalevcenko.vk.modules.auth.vm

import android.arch.lifecycle.ViewModel
import com.example.nikitalevcenko.vk.repo.IAuthRepo
import com.example.nikitalevcenko.vk.router.MAIN_MODULE
import ru.terrakok.cicerone.Router

class AuthViewModel : ViewModel(), IAuthViewModel {
    lateinit var router: Router
    lateinit var authRepo: IAuthRepo

    override fun onAccessTokenReceived(accessToken: String, userId: Long) {
        authRepo.auth(accessToken, userId)
        router.newRootScreen(MAIN_MODULE)
    }
}