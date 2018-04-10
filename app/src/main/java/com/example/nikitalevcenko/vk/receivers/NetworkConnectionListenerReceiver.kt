package com.example.nikitalevcenko.vk.receivers

import android.arch.lifecycle.MutableLiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager


class NetworkConnectionListenerReceiver(context: Context) : BroadcastReceiver(), NetworkConnectionListener {
    override val isConnected by lazy {
        MutableLiveData<Boolean>().apply {
            value = true
        }
    }

    init {
        context.registerReceiver(this, IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        })
    }

    override fun onReceive(context: Context, intent: Intent) {

        val connectivityManage = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val connected = connectivityManage.activeNetworkInfo != null && connectivityManage.activeNetworkInfo!!.isConnectedOrConnecting

        if (connected != isConnected.value) {
            isConnected.value = connected
        }
    }
}