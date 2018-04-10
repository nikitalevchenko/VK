package com.example.nikitalevcenko.vk.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.nikitalevcenko.vk.entity.Profile

object Tables {
    const val PROFILE = "profileLisitng"
}

object DbFields {
    const val ID = "id"
    const val FIRST_NAME = "first_name"
    const val LAST_NAME = "last_name"
    const val SMALL_PHOTO = "small_photo"
}


@Database(
        entities = [(Profile::class)],
        version = 1,
        exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun profiles(): ProfileDao
}