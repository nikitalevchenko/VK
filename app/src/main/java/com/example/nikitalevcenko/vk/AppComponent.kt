package com.example.nikitalevcenko.vk

import com.example.nikitalevcenko.vk.modules.auth.di.AuthComponent
import com.example.nikitalevcenko.vk.modules.auth.di.AuthModule
import com.example.nikitalevcenko.vk.modules.main.di.MainComponent
import com.example.nikitalevcenko.vk.modules.main.di.MainModule
import com.example.nikitalevcenko.vk.modules.more.di.MoreComponent
import com.example.nikitalevcenko.vk.modules.more.di.MoreModule
import com.example.nikitalevcenko.vk.modules.news.di.NewsComponent
import com.example.nikitalevcenko.vk.modules.news.di.NewsModule
import com.example.nikitalevcenko.vk.modules.profile.di.ProfileComponent
import com.example.nikitalevcenko.vk.modules.profile.di.ProfileModule
import com.example.nikitalevcenko.vk.router.NavigationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NavigationModule::class)])
interface AppComponent {
    fun inject(app: App)

    // Subcomponents
    fun plus(module: MainModule): MainComponent

    fun plus(module: NewsModule): NewsComponent
    fun plus(module: AuthModule): AuthComponent
    fun plus(module: MoreModule): MoreComponent
    fun plus(module: ProfileModule): ProfileComponent
}