package com.example.nikitalevcenko.vk

import android.app.Application


class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

        component.inject(this)
    }
}