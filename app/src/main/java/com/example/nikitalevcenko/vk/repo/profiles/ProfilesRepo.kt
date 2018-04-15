package com.example.nikitalevcenko.vk.repo.profiles

import android.arch.lifecycle.MediatorLiveData
import com.example.nikitalevcenko.vk.api.Api
import com.example.nikitalevcenko.vk.db.Database
import com.example.nikitalevcenko.vk.entity.Profile
import com.example.nikitalevcenko.vk.repo.ApiResponseHandler
import com.example.nikitalevcenko.vk.repo.NetworkState
import com.example.nikitalevcenko.vk.settings.SettingsStore
import kotlinx.coroutines.experimental.async

class ProfilesRepo(private val settingsStore: SettingsStore,
                   private val api: Api,
                   private val db: Database) : IProfilesRepo, ApiResponseHandler {

    override fun profileCash(id: Long)= db.profiles().profile(id)

    override fun profile(): ProfileLisitng {
        val dbSource = db.profiles().profile(settingsStore.userId())

        val profile = MediatorLiveData<Profile>().apply {
            addSource(dbSource, {
                value = it
                removeSource(dbSource)
            })
        }

        val networkState = MediatorLiveData<NetworkState>().apply {
            reloadProfile(this, profile)
        }

        return ProfileLisitng(profile = profile,
                networkState = networkState,
                refresh = { reloadProfile(networkState, profile) }
        )
    }

    private fun reloadProfile(networkState: MediatorLiveData<NetworkState>, profile: MediatorLiveData<Profile>) {
        networkState.value = NetworkState.LOADING

        val networkSource = api.loadProfile(settingsStore.accessToken(), settingsStore.userId())

        networkState.addSource(networkSource, { response ->

            networkState.value = handleApiResponse(response = response, map = { profileJsons ->
                val jsonProfile = profileJsons[0]
                return@handleApiResponse Profile(jsonProfile.id,
                        jsonProfile.firstName,
                        jsonProfile.lastName,
                        jsonProfile.photo100)

            }, onSuccess = { profileEntity ->
                async {
                    db.profiles().insertOrReplace(profileEntity)
                }
                profile.value = profileEntity
            })

            networkState.removeSource(networkSource)
        })
    }
}