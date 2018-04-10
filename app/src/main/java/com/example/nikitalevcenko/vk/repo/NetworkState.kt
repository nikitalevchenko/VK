package com.example.nikitalevcenko.vk.repo

import timber.log.Timber

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val throwable: Throwable? = null) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(throwable: Throwable?): NetworkState {
            Timber.e(throwable)
            return NetworkState(Status.FAILED, throwable)
        }
    }
}