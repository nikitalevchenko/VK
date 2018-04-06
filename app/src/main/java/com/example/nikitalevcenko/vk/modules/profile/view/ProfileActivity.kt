package com.example.nikitalevcenko.vk.modules.profile.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.profile.di.ProfileModule
import com.example.nikitalevcenko.vk.modules.profile.vm.IProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: IProfileViewModel

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        App.component.plus(ProfileModule(this)).inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(this.navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                viewModel.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
