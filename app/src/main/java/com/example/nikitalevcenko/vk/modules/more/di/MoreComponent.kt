package com.example.nikitalevcenko.vk.modules.more.di

import com.example.nikitalevcenko.vk.modules.more.view.MoreFragment
import dagger.Subcomponent

@Subcomponent(modules = [(MoreModule::class)])
interface MoreComponent {
    fun inject(moreFragment: MoreFragment)
}