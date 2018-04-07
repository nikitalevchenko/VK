package com.example.nikitalevcenko.vk.modules.profile.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.profile.view.ProfileActivity
import com.example.nikitalevcenko.vk.modules.profile.vm.IProfileViewModel
import com.example.nikitalevcenko.vk.modules.profile.vm.ProfileViewModel
import com.example.nikitalevcenko.vk.router.BaseNavigator
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

@Module
class ProfileModule(private val activity: ProfileActivity) {

    @Provides
    @NonNull
    fun navigator(): Navigator = BaseNavigator(activity, activity.supportFragmentManager, R.id.container)

    @Provides
    @NonNull
    fun viewModel(router: Router): IProfileViewModel {
        val factory = object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProfileViewModel(router) as T
            }
        }

        return ViewModelProviders.of(activity, factory).get(ProfileViewModel::class.java)
    }
}