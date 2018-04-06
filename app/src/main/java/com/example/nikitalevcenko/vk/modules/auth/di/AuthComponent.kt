package com.example.nikitalevcenko.vk.modules.auth.di

import com.example.nikitalevcenko.vk.modules.auth.view.AuthActivity
import dagger.Subcomponent

@Subcomponent(modules = [(AuthModule::class)])
interface AuthComponent {
    fun inject(authActivity: AuthActivity)
}