package com.example.nikitalevcenko.vk.modules.more.di

import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.modules.more.view.MoreFragment
import com.example.nikitalevcenko.vk.modules.more.vm.IMoreViewModel
import com.example.nikitalevcenko.vk.modules.more.vm.MoreViewModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class MoreModule(private val fragment: MoreFragment) {

    @Provides
    @NonNull
    fun viewModel(router: Router): IMoreViewModel {
        val viewModel = ViewModelProviders.of(fragment).get(MoreViewModel::class.java)

        viewModel.router = router

        return viewModel
    }
}