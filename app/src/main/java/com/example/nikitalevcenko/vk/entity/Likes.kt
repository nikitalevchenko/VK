package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import com.example.nikitalevcenko.vk.db.DbFields

data class Likes(

        @ColumnInfo(name = DbFields.COUNT)
        var count: Int,

        @ColumnInfo(name = DbFields.USER_LIKES)
        var userLikes: Boolean,

        @ColumnInfo(name = DbFields.CAN_LIKE)
        var canLike: Boolean,

        @ColumnInfo(name = DbFields.CAN_PUBLISH)
        var canPublish: Boolean
)