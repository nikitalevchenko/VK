package com.example.nikitalevcenko.vk.ui.news.view

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.entity.Attachment
import com.example.nikitalevcenko.vk.entity.Group
import com.example.nikitalevcenko.vk.entity.NewsItem
import com.example.nikitalevcenko.vk.entity.Profile
import com.example.nikitalevcenko.vk.repo.NetworkState

// TODO check another constructor
class NewsAdapter(private val lifecycleOwner: LifecycleOwner,
                  private val attachments: (postId: Long) -> LiveData<List<Attachment>>,
                  private val profile: (id: Long) -> LiveData<Profile>,
                  private val group: (id: Long) -> LiveData<Group>,
                  private val onLike: (newsItem: NewsItem) -> LiveData<NetworkState>,
                  private val onComment: () -> Unit,
                  private val onRepost: () -> Unit)
    : PagedListAdapter<NewsItem, RecyclerView.ViewHolder>(NEWS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_news -> return NewsItemViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news, parent, false),
                    lifecycleOwner = lifecycleOwner,
                    attachments = attachments,
                    profile = profile,
                    group = group,
                    onLike = onLike,
                    onComment = onComment,
                    onRepost = onRepost)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_news -> (holder as NewsItemViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_news
    }

    companion object {
        val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
                    oldItem.postId == newItem.postId && oldItem.sourceId == oldItem.sourceId

            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
                    oldItem.postId == newItem.postId && oldItem.sourceId == oldItem.sourceId

        }
    }
}