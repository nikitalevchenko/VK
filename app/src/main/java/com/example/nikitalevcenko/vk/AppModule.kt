package com.example.nikitalevcenko.vk

import android.arch.persistence.room.Room
import android.content.Context
import android.support.annotation.NonNull
import com.example.nikitalevcenko.vk.api.Api
import com.example.nikitalevcenko.vk.api.BASE_URL
import com.example.nikitalevcenko.vk.api.LiveDataCallAdapterFactory
import com.example.nikitalevcenko.vk.db.Database
import com.example.nikitalevcenko.vk.receivers.NetworkConnectionListener
import com.example.nikitalevcenko.vk.receivers.NetworkConnectionListenerReceiver
import com.example.nikitalevcenko.vk.repo.auth.AuthRepo
import com.example.nikitalevcenko.vk.repo.auth.IAuthRepo
import com.example.nikitalevcenko.vk.repo.groups.GroupsRepo
import com.example.nikitalevcenko.vk.repo.groups.IGroupsRepo
import com.example.nikitalevcenko.vk.repo.news.INewsRepo
import com.example.nikitalevcenko.vk.repo.news.NewsRepo
import com.example.nikitalevcenko.vk.repo.profiles.IProfilesRepo
import com.example.nikitalevcenko.vk.repo.profiles.ProfilesRepo
import com.example.nikitalevcenko.vk.settings.SettingsStore
import com.example.nikitalevcenko.vk.settings.SharedPreferencesSettings
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Provides
    @Singleton
    fun context() = appContext

    @Provides
    @Singleton
    fun networkConnectionListener(context: Context): NetworkConnectionListener {
        return NetworkConnectionListenerReceiver(context)
    }

    @Provides
    @NonNull
    @Singleton
    fun settings(context: Context): SettingsStore = SharedPreferencesSettings(context)

    @Provides
    @NonNull
    @Singleton
    fun appDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "database.db").build()
    }

    @Provides
    @NonNull
    @Singleton
    fun api(): Api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(Api::class.java)

    // Repos
    @Provides
    @NonNull
    @Singleton
    fun authRepo(settingsStore: SettingsStore): IAuthRepo = AuthRepo(settingsStore)

    @Provides
    @NonNull
    @Singleton
    fun profilesRepo(settingsStore: SettingsStore, api: Api, db: Database): IProfilesRepo {
        return ProfilesRepo(settingsStore, api, db)
    }

    @Provides
    @NonNull
    @Singleton
    fun newsRepo(settingsStore: SettingsStore, api: Api, db: Database, context: Context): INewsRepo {
        return NewsRepo(settingsStore, api, db, context)
    }

    @Provides
    @NonNull
    @Singleton
    fun groupsRepo(db: Database): IGroupsRepo {
        return GroupsRepo(db)
    }
}