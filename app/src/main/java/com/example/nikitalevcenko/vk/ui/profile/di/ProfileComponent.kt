package com.example.nikitalevcenko.vk.ui.profile.di

import com.example.nikitalevcenko.vk.ui.profile.view.ProfileActivity
import dagger.Subcomponent

@Subcomponent(modules = [(ProfileModule::class)])
interface ProfileComponent {
    fun inject(profileActivity: ProfileActivity)
}