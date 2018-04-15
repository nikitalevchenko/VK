package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.nikitalevcenko.vk.db.DbFields
import com.example.nikitalevcenko.vk.db.Tables


// TODO foreign keys
@Entity(tableName = Tables.ATTACHMENT/*, foreignKeys = [(ForeignKey(entity = Profile::class,
        parentColumns = [(DbFields.ID)],
        childColumns = [(DbFields.POST_ID)],
        onDelete = ForeignKey.CASCADE))]*/)

data class Attachment(
        @ColumnInfo(name = DbFields.POST_ID)
        @PrimaryKey()
        var postId: Long,


        @ColumnInfo(name = DbFields.TYPE)
        var type: String,

        @Embedded(prefix = "photo_")
        var photo: Photo? = null,

        @Embedded(prefix = "video_")
        var video: Video? = null,

        @Embedded(prefix = "link_")
        var link: Link? = null

)