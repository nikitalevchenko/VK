package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import com.example.nikitalevcenko.vk.db.DbFields

data class Video(

        @ColumnInfo(name = DbFields.ID)
        var videoId: Long,

        @ColumnInfo(name = DbFields.SMALL_PHOTO_URL)
        var smallPhotoUrl: String,

        @ColumnInfo(name = DbFields.PHOTO_URL)
        var photoUrl: String,

        @ColumnInfo(name = DbFields.WIDTH)
        var width: Int,

        @ColumnInfo(name = DbFields.HEIGHT)
        var height: Int
)