package com.example.nikitalevcenko.vk.modules.profile.vm

import android.arch.lifecycle.ViewModel
import ru.terrakok.cicerone.Router

class ProfileViewModel : ViewModel(), IProfileViewModel {

    lateinit var router: Router


    // IProfileViewModel
    override fun onBackPressed() {
        router.exit()
    }
}