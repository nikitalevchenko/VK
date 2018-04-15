package com.example.nikitalevcenko.vk.ui.more.vm

import android.arch.lifecycle.ViewModel
import com.example.nikitalevcenko.vk.repo.Status
import com.example.nikitalevcenko.vk.repo.profiles.IProfilesRepo
import com.example.nikitalevcenko.vk.router.PROFILE_MODULE
import com.example.nikitalevcenko.vk.ui.ViewModelWithRouter
import ru.terrakok.cicerone.Router

class MoreViewModel(override val router: Router,
                    profilesRepo: IProfilesRepo) : ViewModel(), IMoreViewModel, ViewModelWithRouter {

    override val profileLisitng by lazy {
        profilesRepo.profile().apply {
            networkState.observeForever { state ->
                if (state?.status == Status.FAILED) {
                    handleError(state.throwable)
                }
            }
        }
    }

    // IMoreViewModel
    override fun onGoToProfileCLick() {
        router.navigateTo(PROFILE_MODULE)
    }

    override fun onRefresh() {
        profileLisitng.refresh()
    }

    override fun onNetworkConnectionChanged(connected: Boolean) {
        if (connected && profileLisitng.networkState.value?.status == Status.FAILED) {
            profileLisitng.refresh()
        }
    }
}