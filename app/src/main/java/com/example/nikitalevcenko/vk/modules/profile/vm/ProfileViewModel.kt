package com.example.nikitalevcenko.vk.modules.profile.vm

import android.arch.lifecycle.ViewModel
import ru.terrakok.cicerone.Router

class ProfileViewModel(private val router: Router) : ViewModel(), IProfileViewModel {


    // IProfileViewModel
    override fun onBackPressed() {
        router.exit()
    }
}