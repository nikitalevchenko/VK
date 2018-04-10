package com.example.nikitalevcenko.vk

import com.example.nikitalevcenko.vk.router.NavigationModule
import com.example.nikitalevcenko.vk.ui.auth.di.AuthComponent
import com.example.nikitalevcenko.vk.ui.auth.di.AuthModule
import com.example.nikitalevcenko.vk.ui.main.di.MainComponent
import com.example.nikitalevcenko.vk.ui.main.di.MainModule
import com.example.nikitalevcenko.vk.ui.more.di.MoreComponent
import com.example.nikitalevcenko.vk.ui.more.di.MoreModule
import com.example.nikitalevcenko.vk.ui.news.di.NewsComponent
import com.example.nikitalevcenko.vk.ui.news.di.NewsModule
import com.example.nikitalevcenko.vk.ui.profile.di.ProfileComponent
import com.example.nikitalevcenko.vk.ui.profile.di.ProfileModule
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
