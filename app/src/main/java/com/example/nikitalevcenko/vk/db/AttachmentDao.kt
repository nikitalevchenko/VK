package com.example.nikitalevcenko.vk.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.nikitalevcenko.vk.entity.Attachment
import com.example.nikitalevcenko.vk.entity.NewsItem

@Dao
interface AttachmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(attachments: List<Attachment>)

    @Query("SELECT * FROM ${Tables.ATTACHMENT} WHERE ${DbFields.POST_ID} = :postId")
    fun attachmentsOfPost(postId: Long): LiveData<List<Attachment>>
}