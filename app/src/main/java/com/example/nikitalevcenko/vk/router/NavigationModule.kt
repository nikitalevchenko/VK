package com.example.nikitalevcenko.vk.router

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone by lazy {
        Cicerone.create()
    }

    @Provides
    @Singleton
    internal fun router() = cicerone.router

    @Provides
    @Singleton
    internal fun navigatorHolder() = cicerone.navigatorHolder
}