package com.example.nikitalevcenko.vk.ui.news.vm

import android.arch.lifecycle.ViewModel
import com.example.nikitalevcenko.vk.entity.NewsItem
import com.example.nikitalevcenko.vk.repo.Status
import com.example.nikitalevcenko.vk.repo.groups.IGroupsRepo
import com.example.nikitalevcenko.vk.repo.news.INewsRepo
import com.example.nikitalevcenko.vk.repo.profiles.IProfilesRepo
import com.example.nikitalevcenko.vk.router.PROFILE_MODULE
import com.example.nikitalevcenko.vk.ui.ViewModelWithRouter
import ru.terrakok.cicerone.Router

class NewsViewModel(override val router: Router,
                    private val newsRepo: INewsRepo,
                    profilesRepo: IProfilesRepo,
                    groupsRepo: IGroupsRepo) : ViewModel(), INewsViewModel, ViewModelWithRouter {

    override val newsListing by lazy {
        newsRepo.news().apply {
            networkState.observeForever { state ->
                if (state?.status == Status.FAILED) {
                    handleError(state.throwable)
                }
            }
        }
    }

    override val attachments = newsRepo::attachments

    override val profile = profilesRepo::profileCash

    override val group = groupsRepo::groupCash

    // INewsViewModel
    override fun onGoToProfileCLick() {
        router.navigateTo(PROFILE_MODULE)
    }

    override fun onRefresh() {
        newsListing.refresh()
    }

    override fun onLike(newsItem: NewsItem) = newsRepo.like(newsItem)
}