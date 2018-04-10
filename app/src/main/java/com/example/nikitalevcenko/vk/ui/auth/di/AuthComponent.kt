package com.example.nikitalevcenko.vk.ui.auth.di

import com.example.nikitalevcenko.vk.ui.auth.view.AuthActivity
import dagger.Subcomponent

@Subcomponent(modules = [(AuthModule::class)])
interface AuthComponent {
    fun inject(authActivity: AuthActivity)
}