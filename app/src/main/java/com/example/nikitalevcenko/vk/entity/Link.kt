package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import com.example.nikitalevcenko.vk.db.DbFields

data class Link(
        @Embedded
        var photo: Photo
)