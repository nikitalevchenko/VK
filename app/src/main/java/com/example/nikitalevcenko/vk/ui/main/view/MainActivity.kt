package com.example.nikitalevcenko.vk.ui.main.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.receivers.NetworkConnectionListener
import com.example.nikitalevcenko.vk.ui.main.di.MainModule
import com.example.nikitalevcenko.vk.ui.main.vm.IMainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_no_network.*
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

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    private val networkConnectionObserver by lazy {
        Observer<Boolean> { isConnected ->
            if (isConnected!!) {
                networkConnectionLinearLayout.visibility = View.GONE
                if (viewModel.title.value != null) {
                    setTitle(viewModel.title.value!!)
                }
            } else {
                appBarLayout.setExpanded(true)
                networkConnectionLinearLayout.visibility = View.VISIBLE
                title = ""
            }
        }
    }

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
            if (networkConnectionListener.isConnected.value!!) setTitle(title!!)
        })

        networkConnectionListener.isConnected.observe(this, networkConnectionObserver)

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

    override fun onDestroy() {
        super.onDestroy()
        networkConnectionListener.isConnected.removeObserver(networkConnectionObserver)
    }
}
