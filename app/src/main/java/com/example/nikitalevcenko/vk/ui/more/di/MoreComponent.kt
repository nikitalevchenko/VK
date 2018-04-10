package com.example.nikitalevcenko.vk.ui.more.di

import com.example.nikitalevcenko.vk.ui.more.view.MoreFragment
import dagger.Subcomponent

@Subcomponent(modules = [(MoreModule::class)])
interface MoreComponent {
    fun inject(moreFragment: MoreFragment)
}