package com.example.nikitalevcenko.vk.modules.auth.view

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.auth.di.AuthModule
import com.example.nikitalevcenko.vk.modules.auth.vm.IAuthViewModel
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class AuthActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModel: IAuthViewModel

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        App.component.plus(AuthModule(this)).inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(this.navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}
