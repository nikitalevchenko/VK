package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.nikitalevcenko.vk.db.DbFields
import com.example.nikitalevcenko.vk.db.Tables

@Entity(tableName = Tables.NEWS)
data class NewsItem(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,

        @ColumnInfo(name = DbFields.POST_ID)
        var postId: Long,

        @ColumnInfo(name = DbFields.TYPE)
        var type: String,

        @ColumnInfo(name = DbFields.TEXT)
        var text: String?,

        @ColumnInfo(name = DbFields.SOURCE_ID)
        var sourceId: Long,

        @ColumnInfo(name = DbFields.OWNER_ID)
        var ownerId: Long,

        @ColumnInfo(name = DbFields.DATE)
        var sourceDate: Long,

        @ColumnInfo(name = DbFields.OWNER_DATE)
        var ownerDate: Long,

        @Embedded(prefix = "comments_")
        var comments: Comments,

        @Embedded(prefix = "likes_")
        var likes: Likes,

        @Embedded(prefix = "reposts_")
        var reposts: Reposts,

        @Embedded(prefix = "views_")
        var views: Views
)