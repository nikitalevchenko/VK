package com.example.nikitalevcenko.vk.ui.main.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.repo.auth.IAuthRepo
import com.example.nikitalevcenko.vk.router.BaseNavigator
import com.example.nikitalevcenko.vk.ui.main.view.MainActivity
import com.example.nikitalevcenko.vk.ui.main.vm.IMainViewModel
import com.example.nikitalevcenko.vk.ui.main.vm.MainViewModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

@Module
class MainModule(private val activity: MainActivity) {

    @Provides
    @NonNull
    fun navigator(): Navigator = BaseNavigator(activity, activity.supportFragmentManager, R.id.container)

    @Provides
    @NonNull
    fun viewModel(router: Router, authRepo: IAuthRepo): IMainViewModel {
        val factory = object : ViewModelProvider.NewInstanceFactory() {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(router, authRepo) as T
            }
        }

        return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
    }
}