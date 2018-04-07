package com.example.nikitalevcenko.vk.modules.auth.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.auth.view.AuthActivity
import com.example.nikitalevcenko.vk.modules.auth.vm.AuthViewModel
import com.example.nikitalevcenko.vk.modules.auth.vm.IAuthViewModel
import com.example.nikitalevcenko.vk.repo.IAuthRepo
import com.example.nikitalevcenko.vk.router.BaseNavigator
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

@Module
class AuthModule(private val activity: AuthActivity) {

    @Provides
    @NonNull
    fun navigator(): Navigator = BaseNavigator(activity, activity.supportFragmentManager, R.id.container)

    @Provides
    @NonNull
    fun viewModel(router: Router, authRepo: IAuthRepo): IAuthViewModel {

        val factory = object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AuthViewModel(router, authRepo) as T
            }
        }

        return ViewModelProviders.of(activity, factory).get(AuthViewModel::class.java)
    }
}