package com.example.nikitalevcenko.vk.ui.news.vm

import android.arch.lifecycle.LiveData
import com.example.nikitalevcenko.vk.entity.Attachment
import com.example.nikitalevcenko.vk.entity.Group
import com.example.nikitalevcenko.vk.entity.NewsItem
import com.example.nikitalevcenko.vk.entity.Profile
import com.example.nikitalevcenko.vk.repo.NetworkState
import com.example.nikitalevcenko.vk.repo.news.NewsLisitng

interface INewsViewModel {
    val newsListing: NewsLisitng

    val attachments: (postId: Long) -> LiveData<List<Attachment>>
    val profile: (id: Long) -> LiveData<Profile>
    val group: (id: Long) -> LiveData<Group>

    fun onLike(newsItem: NewsItem): LiveData<NetworkState>

    fun onGoToProfileCLick()
    fun onRefresh()
}