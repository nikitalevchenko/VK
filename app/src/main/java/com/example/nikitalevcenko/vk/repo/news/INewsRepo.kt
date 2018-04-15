package com.example.nikitalevcenko.vk.repo.news

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.example.nikitalevcenko.vk.entity.Attachment
import com.example.nikitalevcenko.vk.entity.NewsItem
import com.example.nikitalevcenko.vk.repo.NetworkState

interface INewsRepo {
    fun news(): NewsLisitng
    fun attachments(postId: Long): LiveData<List<Attachment>>
    fun like(newsItem: NewsItem): LiveData<NetworkState>
}

data class NewsLisitng(val news: LiveData<PagedList<NewsItem>>,
                       val networkState: LiveData<NetworkState>,
                       val refresh: () -> Unit)