package com.example.nikitalevcenko.vk.ui.auth.vm

import android.arch.lifecycle.ViewModel
import com.example.nikitalevcenko.vk.repo.auth.IAuthRepo
import com.example.nikitalevcenko.vk.router.MAIN_MODULE
import ru.terrakok.cicerone.Router

class AuthViewModel(private val router: Router, private val authRepo: IAuthRepo) : ViewModel(), IAuthViewModel {

    override fun onAccessTokenReceived(accessToken: String, userId: Long) {
        authRepo.auth(accessToken, userId)
        router.newRootScreen(MAIN_MODULE)
    }
}