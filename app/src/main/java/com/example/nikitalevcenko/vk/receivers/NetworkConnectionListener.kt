package com.example.nikitalevcenko.vk.receivers

import android.arch.lifecycle.LiveData

interface NetworkConnectionListener {
    val isConnected: LiveData<Boolean>
}