package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import com.example.nikitalevcenko.vk.db.DbFields

data class Comments(

        @ColumnInfo(name = DbFields.COUNT)
        var count: Int,

        @ColumnInfo(name = DbFields.CAN_POST)
        var canPost: Boolean
)