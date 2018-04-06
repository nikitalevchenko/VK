package com.example.nikitalevcenko.vk.modules.more.vm

import android.arch.lifecycle.ViewModel
import com.example.nikitalevcenko.vk.router.PROFILE_MODULE
import ru.terrakok.cicerone.Router

class MoreViewModel : ViewModel(), IMoreViewModel {

    lateinit var router: Router

    // IMoreViewModel
    override fun onGoToProfileCLick() {
        router.navigateTo(PROFILE_MODULE)
    }
}