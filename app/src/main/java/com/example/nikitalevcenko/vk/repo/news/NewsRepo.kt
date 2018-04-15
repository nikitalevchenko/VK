package com.example.nikitalevcenko.vk.repo.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.example.nikitalevcenko.vk.api.*
import com.example.nikitalevcenko.vk.db.Database
import com.example.nikitalevcenko.vk.entity.*
import com.example.nikitalevcenko.vk.repo.ApiResponseHandler
import com.example.nikitalevcenko.vk.repo.NetworkState
import com.example.nikitalevcenko.vk.settings.SettingsStore
import kotlinx.coroutines.experimental.async
import java.util.concurrent.Executors


private const val DEFAULT_NETWORK_PAGE_SIZE = 20

class NewsRepo(private val settingsStore: SettingsStore,
               private val api: Api,
               private val db: Database,
               context: Context) : INewsRepo, ApiResponseHandler {

    @Suppress("PrivatePropertyName")
    private val DISK_IO = Executors.newSingleThreadExecutor()

    private var startFrom: String = ""

    private var isLoadingNow = false

    private val screenWidth by lazy {
        context.resources.displayMetrics.widthPixels
    }

    override fun attachments(postId: Long) = db.attachments().attachmentsOfPost(postId)

    override fun like(newsItem: NewsItem): LiveData<NetworkState> {
        val userLikes = !newsItem.likes.userLikes

        val networkSource: LiveData<ApiResponse<JsonResponse<LikesCountJson>>>

        if (userLikes) {
            networkSource = api.addLike(accessToken = settingsStore.accessToken(),
                    type = newsItem.type,
                    ownerId = newsItem.sourceId,
                    itemId = newsItem.postId)
        } else {
            networkSource = api.deleteLike(accessToken = settingsStore.accessToken(),
                    type = newsItem.type,
                    ownerId = newsItem.sourceId,
                    itemId = newsItem.postId)
        }

        return Transformations.switchMap(networkSource, { apiResponse ->
            object : LiveData<NetworkState>() {
                override fun onActive() {
                    super.onActive()
                    postValue(NetworkState.LOADING)
                    postValue(handleApiResponse(response = apiResponse,
                            map = { likesCountJson ->
                                return@handleApiResponse likesCountJson.likesCount
                            },
                            onSuccess = { likesCount ->
                                newsItem.likes.count = likesCount
                                newsItem.likes.userLikes = userLikes
                                DISK_IO.execute {
                                    db.news().insertOrReplace(newsItem)
                                }
                            },
                            onError = {
                                newsItem.likes.userLikes = !userLikes
                                DISK_IO.execute {
                                    db.news().insertOrReplace(newsItem)
                                }
                            }))
                }

            }
        })
    }

    override fun news(): NewsLisitng {

        val networkState = MediatorLiveData<NetworkState>()

        val boundaryCallback = object : PagedList.BoundaryCallback<NewsItem>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                loadNews(networkState)
            }

            override fun onItemAtEndLoaded(itemAtEnd: NewsItem) {
                super.onItemAtEndLoaded(itemAtEnd)
                loadNews(networkState)
            }

        }

        val builder = LivePagedListBuilder(db.news().news(), DEFAULT_NETWORK_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)

        return NewsLisitng(news = builder.build(),
                networkState = networkState,
                refresh = {
                    // TODO отменять идущий запрос
                    startFrom = ""
                    loadNews(networkState)
                })
                .apply { refresh() }
    }

    private fun loadNews(networkState: MediatorLiveData<NetworkState>) {
        if (isLoadingNow) return

        isLoadingNow = true

        val networkSource = api.loadNews(accessToken = settingsStore.accessToken(),
                count = DEFAULT_NETWORK_PAGE_SIZE,
                startFrom = startFrom)

        networkState.addSource(networkSource, { response ->
            networkState.value = handleApiResponse(response, { newsJson ->
                async {
                    saveNewsIntoDb(newsJson)

                    startFrom = newsJson.nextFrom

                    isLoadingNow = false
                }
            })

            networkState.removeSource(networkSource)
        })
    }

    private fun saveNewsIntoDb(newsJson: NewsJson) {

        val newsItems = ArrayList<NewsItem>()
        val attachments = ArrayList<Attachment>()

        val profiles = newsJson.profiles.map { profileJson ->
            Profile(profileJson.id,
                    profileJson.firstName,
                    profileJson.lastName,
                    profileJson.photo100)
        }

        val groups = newsJson.groups.map { groupJson ->
            Group(groupJson.id,
                    groupJson.name,
                    groupJson.photo100)
        }

        newsJson.items.forEach({ newsItemJson ->
            newsItems.add(NewsItem(
                    postId = newsItemJson.postId,
                    type = newsItemJson.type,
                    text = newsItemJson.text,
                    sourceId = newsItemJson.sourceId,
                    ownerId = if (newsItemJson.copyHistory == null) newsItemJson.sourceId else newsItemJson.copyHistory[0].ownerId,
                    sourceDate = newsItemJson.date,
                    ownerDate = if (newsItemJson.copyHistory == null) newsItemJson.date else newsItemJson.copyHistory[0].ownerDate,
                    comments = Comments(count = newsItemJson.comments.count,
                            canPost = newsItemJson.comments.canPost == 1),
                    likes = Likes(count = newsItemJson.likes.count,
                            userLikes = newsItemJson.likes.userLikes == 1,
                            canLike = newsItemJson.likes.canLike == 1,
                            canPublish = newsItemJson.likes.canPublish == 1),
                    reposts = Reposts(count = newsItemJson.reposts.count,
                            userReposted = newsItemJson.reposts.userReposted == 1),
                    views = Views(count = newsItemJson.reposts.count)
            ))
            newsItemJson.attachments?.forEach { attachmentJson ->
                attachments.add(Attachment(
                        postId = newsItemJson.postId,
                        type = attachmentJson.type,
                        photo = if (attachmentJson.photo == null) null else {
                            Photo(photoId = attachmentJson.photo.id,
                                    photoUrl = if (screenWidth > 807 && attachmentJson.photo.photo1280 != null) {
                                        attachmentJson.photo.photo1280
                                    } else if (screenWidth > 604 && attachmentJson.photo.photo807 != null) {
                                        attachmentJson.photo.photo807
                                    } else {
                                        attachmentJson.photo.photo604!!
                                    },
                                    smallPhotoUrl = attachmentJson.photo.photo130!!,
                                    width = attachmentJson.photo.width,
                                    height = attachmentJson.photo.height)
                        },
                        video = if (attachmentJson.video == null) null else {
                            Video(videoId = attachmentJson.video.id,
                                    photoUrl = if (screenWidth > 640 && attachmentJson.video.photo800 != null) {
                                        attachmentJson.video.photo800
                                    } else if (screenWidth > 320 && attachmentJson.video.photo640 != null) {
                                        attachmentJson.video.photo640
                                    } else {
                                        attachmentJson.video.photo130!!
                                    },
                                    smallPhotoUrl = attachmentJson.video.photo130!!,
                                    width = attachmentJson.video.width,
                                    height = attachmentJson.video.height)
                        },
                        link = if (attachmentJson.link == null) null else {
                            Link(Photo(photoId = attachmentJson.link.photo.id,
                                    photoUrl = if (screenWidth > 807 && attachmentJson.link.photo.photo1280 != null) {
                                        attachmentJson.link.photo.photo1280
                                    } else if (screenWidth > 604 && attachmentJson.link.photo.photo807 != null) {
                                        attachmentJson.link.photo.photo807
                                    } else {
                                        attachmentJson.link.photo.photo604!!
                                    },
                                    smallPhotoUrl = attachmentJson.link.photo.photo130!!,
                                    width = attachmentJson.link.photo.width,
                                    height = attachmentJson.link.photo.height))
                        }
                ))
            }
        })

        db.runInTransaction {

            if (startFrom.isEmpty()) {
                db.news().clear()
            }

            db.news().insertOrReplace(newsItems)
            db.attachments().insertOrReplace(attachments)
            db.profiles().insertOrReplace(profiles)
            db.groups().insertOrReplace(groups)
        }
    }
}