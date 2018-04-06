package com.example.nikitalevcenko.vk.router

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.example.nikitalevcenko.vk.base.extensions.showMessage
import com.example.nikitalevcenko.vk.modules.auth.view.AuthActivity
import com.example.nikitalevcenko.vk.modules.main.view.MainActivity
import com.example.nikitalevcenko.vk.modules.more.view.MoreFragment
import com.example.nikitalevcenko.vk.modules.news.view.NewsFragment
import com.example.nikitalevcenko.vk.modules.profile.view.ProfileActivity
import ru.terrakok.cicerone.android.SupportAppNavigator

const val MAIN_MODULE = "MAIN_MODULE"
const val AUTH_MODULE = "AUTH_MODULE"
const val NEWS_MODULE = "NEWS_MODULE"
const val MORE_MODULE = "MORE_MODULE"
const val PROFILE_MODULE = "PROFILE_MODULE"

open class BaseNavigator(private val activity: FragmentActivity, fragmentManager: FragmentManager, containerId: Int)
    : SupportAppNavigator(activity, fragmentManager, containerId) {
    override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
        return when (screenKey) {
            AUTH_MODULE -> Intent(context, AuthActivity::class.java)
            MAIN_MODULE -> Intent(context, MainActivity::class.java)
            PROFILE_MODULE -> Intent(context, ProfileActivity::class.java)
            else -> null
        }
    }

    override fun createFragment(screenKey: String, data: Any?): Fragment? {
        return when (screenKey) {
            NEWS_MODULE -> NewsFragment.newInstance()
            MORE_MODULE -> MoreFragment.newInstance()
            else -> null
        }
    }

    override fun showSystemMessage(message: String?) {
        if (message != null) activity.showMessage(message)
    }
}