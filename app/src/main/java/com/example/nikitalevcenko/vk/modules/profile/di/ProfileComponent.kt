package com.example.nikitalevcenko.vk.modules.profile.di

import com.example.nikitalevcenko.vk.modules.profile.view.ProfileActivity
import dagger.Subcomponent

@Subcomponent(modules = [(ProfileModule::class)])
interface ProfileComponent {
    fun inject(profileActivity: ProfileActivity)
}