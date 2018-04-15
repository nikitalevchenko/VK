package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import com.example.nikitalevcenko.vk.db.DbFields

data class Reposts(

        @ColumnInfo(name = DbFields.COUNT)
        var count: Int,

        @ColumnInfo(name = DbFields.USER_REPOSTED)
        var userReposted: Boolean
)