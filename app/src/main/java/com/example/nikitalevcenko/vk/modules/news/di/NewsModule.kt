package com.example.nikitalevcenko.vk.modules.news.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.modules.news.view.NewsFragment
import com.example.nikitalevcenko.vk.modules.news.vm.INewsViewModel
import com.example.nikitalevcenko.vk.modules.news.vm.NewsViewModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class NewsModule(private val fragment: NewsFragment) {

    @Provides
    @NonNull
    fun viewModel(router: Router): INewsViewModel {
        val factory = object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsViewModel(router) as T
            }
        }

        return ViewModelProviders.of(fragment, factory).get(NewsViewModel::class.java)
    }
}