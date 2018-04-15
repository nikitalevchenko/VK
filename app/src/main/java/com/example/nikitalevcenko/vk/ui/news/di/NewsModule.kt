package com.example.nikitalevcenko.vk.ui.news.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.repo.groups.GroupsRepo
import com.example.nikitalevcenko.vk.repo.groups.IGroupsRepo
import com.example.nikitalevcenko.vk.repo.news.INewsRepo
import com.example.nikitalevcenko.vk.repo.profiles.IProfilesRepo
import com.example.nikitalevcenko.vk.ui.news.view.NewsFragment
import com.example.nikitalevcenko.vk.ui.news.vm.INewsViewModel
import com.example.nikitalevcenko.vk.ui.news.vm.NewsViewModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class NewsModule(private val fragment: NewsFragment) {

    @Provides
    @NonNull
    fun viewModel(router: Router, newsRepo: INewsRepo, profilesRepo: IProfilesRepo, groupsRepo: IGroupsRepo): INewsViewModel {
        val factory = object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsViewModel(router, newsRepo, profilesRepo, groupsRepo) as T
            }
        }

        return ViewModelProviders.of(fragment, factory).get(NewsViewModel::class.java)
    }
}