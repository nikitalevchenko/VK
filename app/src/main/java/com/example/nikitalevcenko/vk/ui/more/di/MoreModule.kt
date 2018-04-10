package com.example.nikitalevcenko.vk.ui.more.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.repo.profiles.IProfilesRepo
import com.example.nikitalevcenko.vk.ui.more.view.MoreFragment
import com.example.nikitalevcenko.vk.ui.more.vm.IMoreViewModel
import com.example.nikitalevcenko.vk.ui.more.vm.MoreViewModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class MoreModule(private val fragment: MoreFragment) {

    @Provides
    @NonNull
    fun viewModel(router: Router, profilesRepo: IProfilesRepo): IMoreViewModel {

        val factory = object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MoreViewModel(router, profilesRepo) as T
            }
        }

        return ViewModelProviders.of(fragment, factory).get(MoreViewModel::class.java)
    }
}