package com.example.nikitalevcenko.vk.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.nikitalevcenko.vk.entity.Group

@Dao
abstract class GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(groups: List<Group>)

    @Query("SELECT * FROM ${Tables.GROUP} WHERE ${DbFields.ID} = :id")
    abstract fun group(id: Long): LiveData<Group>
}