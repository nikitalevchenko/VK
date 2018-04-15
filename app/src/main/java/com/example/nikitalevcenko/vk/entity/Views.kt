package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import com.example.nikitalevcenko.vk.db.DbFields

data class Views(

        @ColumnInfo(name = DbFields.COUNT)
        var count: Int
)