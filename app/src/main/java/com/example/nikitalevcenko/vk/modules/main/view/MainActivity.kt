package com.example.nikitalevcenko.vk.modules.main.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.main.di.MainModule
import com.example.nikitalevcenko.vk.modules.main.vm.IMainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModel: IMainViewModel

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    @Inject
    lateinit var navigator: Navigator

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        App.component.plus(MainModule(this)).inject(this)

        navigation.setOnNavigationItemSelectedListener { item ->
            if (navigation.selectedItemId != item.itemId) {
                viewModel.onNavigationItemSelected(item)
            }
            true
        }

        viewModel.currentItemId.observe(this, Observer { id ->
            if (navigation.selectedItemId != id) {
                navigation.menu.findItem(id!!).isChecked = true
            }
        })

        viewModel.title.observe(this, Observer { title ->
            setTitle(title!!)
        })

        if (lifecycleRegistry.observerCount == 0) lifecycleRegistry.addObserver(viewModel)

        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(this.navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}
