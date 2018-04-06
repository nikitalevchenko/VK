package com.example.nikitalevcenko.vk.modules.main.di

import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.main.view.MainActivity
import com.example.nikitalevcenko.vk.modules.main.vm.IMainViewModel
import com.example.nikitalevcenko.vk.modules.main.vm.MainViewModel
import com.example.nikitalevcenko.vk.router.BaseNavigator
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
    fun viewModel(router: Router): IMainViewModel {
        val viewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)

        viewModel.router = router

        return viewModel
    }
}