package com.example.nikitalevcenko.vk.ui.main.di

import com.example.nikitalevcenko.vk.ui.main.view.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [(MainModule::class)])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}