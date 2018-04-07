package com.example.nikitalevcenko.vk

import android.content.Context
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.repo.AuthRepo
import com.example.nikitalevcenko.vk.repo.IAuthRepo
import com.example.nikitalevcenko.vk.settings.SettingsStore
import com.example.nikitalevcenko.vk.settings.SharedPreferencesSettings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Provides
    @Singleton
    fun context() = appContext

    @Provides
    @NonNull
    @Singleton
    fun provideSettings(context: Context): SettingsStore = SharedPreferencesSettings(context)

    // Repos
    @Provides
    @NonNull
    @Singleton
    fun provideAuthRepo(settingsStore: SettingsStore): IAuthRepo = AuthRepo(settingsStore)
}