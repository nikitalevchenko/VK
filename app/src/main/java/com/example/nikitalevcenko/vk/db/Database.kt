package com.example.nikitalevcenko.vk.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.nikitalevcenko.vk.entity.Attachment
import com.example.nikitalevcenko.vk.entity.Group
import com.example.nikitalevcenko.vk.entity.NewsItem
import com.example.nikitalevcenko.vk.entity.Profile

object Tables {
    const val PROFILE = "profile_table"
    const val NEWS = "news_table"
    const val LIKE = "like_table"
    const val ATTACHMENT = "attachment_table"
    const val GROUP = "group_table"
}

object DbFields {
    const val ID = "id"
    const val FIRST_NAME = "first_name"
    const val LAST_NAME = "last_name"
    const val SMALL_PHOTO = "small_photo"
    const val TYPE = "type"
    const val DATE = "date"
    const val SOURCE_ID = "source_id"
    const val POST_ID = "post_id"
    const val PHOTO_URL = "photo_url"
    const val SMALL_PHOTO_URL = "small_photo_url"
    const val WIDTH = "width"
    const val HEIGHT = "height"
    const val TEXT = "text"
    const val NAME = "name"
    const val COUNT = "count"
    const val USER_LIKES = "user_likes"
    const val CAN_LIKE = "can_like"
    const val CAN_PUBLISH = "can_publish"
    const val CAN_POST = "can_post"
    const val USER_REPOSTED = "user_reposted"
    const val OWNER_ID = "owner_id"
    const val OWNER_DATE = "owner_date"
}


@Database(
        entities = [(Profile::class), (NewsItem::class), (Attachment::class), (Group::class)],
        version = 1,
        exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun profiles(): ProfileDao
    abstract fun groups(): GroupDao
    abstract fun news(): NewsDao
    abstract fun attachments(): AttachmentDao
}