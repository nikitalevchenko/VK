package com.example.nikitalevcenko.vk.modules.news.di

import com.example.nikitalevcenko.vk.modules.news.view.NewsFragment
import dagger.Subcomponent

@Subcomponent(modules = [(NewsModule::class)])
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}