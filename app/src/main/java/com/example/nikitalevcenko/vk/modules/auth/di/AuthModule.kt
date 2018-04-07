package com.example.nikitalevcenko.vk.modules.auth.di

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
        val viewModel = ViewModelProviders.of(activity).get(AuthViewModel::class.java)

        viewModel.router = router
        viewModel.authRepo = authRepo

        return viewModel
    }
}