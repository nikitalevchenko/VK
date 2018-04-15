package com.example.nikitalevcenko.vk.db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.nikitalevcenko.vk.entity.NewsItem

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(news: List<NewsItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(newsItem: NewsItem)

    @Query("SELECT * FROM ${Tables.NEWS}")
    fun news(): DataSource.Factory<Int, NewsItem>

    @Query("DELETE FROM ${Tables.NEWS}")
    fun clear()
}