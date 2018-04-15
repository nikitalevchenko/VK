package com.example.nikitalevcenko.vk.repo.profiles

import android.arch.lifecycle.LiveData
import com.example.nikitalevcenko.vk.entity.Profile
import com.example.nikitalevcenko.vk.repo.NetworkState

interface IProfilesRepo {
    fun profile(): ProfileLisitng
    fun profileCash(id: Long): LiveData<Profile>
}

data class ProfileLisitng(val profile: LiveData<Profile>,
                          val networkState: LiveData<NetworkState>,
                          val refresh: () -> Unit)