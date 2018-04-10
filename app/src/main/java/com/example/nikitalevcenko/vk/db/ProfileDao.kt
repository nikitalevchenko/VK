package com.example.nikitalevcenko.vk.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.nikitalevcenko.vk.entity.Profile


@Dao
abstract class ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(profile: Profile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(profiles: List<Profile>)

    @Query("SELECT * FROM ${Tables.PROFILE} WHERE ${DbFields.ID} = :id")
    abstract fun profile(id: Long): LiveData<Profile>
}