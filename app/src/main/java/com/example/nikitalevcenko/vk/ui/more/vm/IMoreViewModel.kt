package com.example.nikitalevcenko.vk.ui.more.vm

import com.example.nikitalevcenko.vk.repo.profiles.ProfileLisitng

interface IMoreViewModel {
    val profileLisitng: ProfileLisitng

    fun onGoToProfileCLick()
    fun onNetworkConnectionChanged(connected: Boolean)
    fun onRefresh()
}