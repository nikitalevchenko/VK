package com.example.nikitalevcenko.vk.ui.main.vm

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.view.MenuItem

interface IMainViewModel : LifecycleObserver {
    val currentItemId: LiveData<Int>
    val title: LiveData<Int>

    fun onNavigationItemSelected(item: MenuItem)
    fun onBackPressed()
}