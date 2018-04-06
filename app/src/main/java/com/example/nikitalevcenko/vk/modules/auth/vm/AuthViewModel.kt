package com.example.nikitalevcenko.vk.modules.auth.vm

import android.arch.lifecycle.ViewModel
import ru.terrakok.cicerone.Router

class AuthViewModel : ViewModel(), IAuthViewModel {

    lateinit var router: Router
}