package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.nikitalevcenko.vk.db.DbFields
import com.example.nikitalevcenko.vk.db.Tables

@Entity(tableName = Tables.GROUP)
data class Group(

        @ColumnInfo(name = DbFields.ID)
        @PrimaryKey()
        var id: Long,

        @ColumnInfo(name = DbFields.NAME)
        var name: String,

        @ColumnInfo(name = DbFields.SMALL_PHOTO)
        var smallPhoto: String?
)