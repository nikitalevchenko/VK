package com.example.nikitalevcenko.vk.ui.news.vm

import android.arch.lifecycle.ViewModel
import com.example.nikitalevcenko.vk.router.PROFILE_MODULE
import ru.terrakok.cicerone.Router

class NewsViewModel(private val router: Router) : ViewModel(), INewsViewModel {

    // INewsViewModel
    override fun onGoToProfileCLick() {
        router.navigateTo(PROFILE_MODULE)
    }
}