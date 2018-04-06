package com.example.nikitalevcenko.vk.modules.main.di

import com.example.nikitalevcenko.vk.modules.main.view.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [(MainModule::class)])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}