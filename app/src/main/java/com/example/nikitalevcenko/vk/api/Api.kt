package com.example.nikitalevcenko.vk.api

import android.arch.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.vk.com/method/"

object ApiFields {
    const val ACCESS_TOKEN = "access_token"
    const val FIRST_NAME = "first_name"
    const val LAST_NAME = "last_name"
    const val PHOTO_100 = "photo_100"
    const val ID = "id"
    const val V = "v"
    const val USER_IDS = "user_ids"
    const val FIELDS = "fields"
    const val RESPONSE = "response"
    const val FILTERS = "filters"
    const val POST = "post"
    const val COUNT = "count"
    const val START_FROM = "start_from"
    const val ITEMS = "items"
    const val TYPE = "type"
    const val DATE = "date"
    const val NEXT_FROM = "next_from"
    const val SOURCE_ID = "source_id"
    const val POST_ID = "post_id"
    const val ATTACHMENTS = "attachments"
    const val PHOTO_75 = "photo_75"
    const val PHOTO_130 = "photo_130"
    const val PHOTO_320 = "photo_320"
    const val PHOTO_640 = "photo_640"
    const val PHOTO_800 = "photo_800"
    const val PHOTO_604 = "photo_604"
    const val PHOTO_807 = "photo_807"
    const val PHOTO_1280 = "photo_1280"
    const val PHOTO_2560 = "photo_2560"
    const val PHOTO = "photo"
    const val WIDTH = "width"
    const val HEIGHT = "height"
    const val PROFILES = "profiles"
    const val TEXT = "text"
    const val NAME = "name"
    const val GROUPS = "groups"
    const val LIKES = "likes"
    const val USER_LIKES = "user_likes"
    const val CAN_LIKE = "can_like"
    const val CAN_PUBLISH = "can_publish"
    const val CAN_POST = "can_post"
    const val USER_REPOSTED = "user_reposted"
    const val COMMENTS = "comments"
    const val REPOSTS = "reposts"
    const val VIEWS = "views"
    const val ITEM_ID = "item_id"
    const val OWNER_ID = "owner_id"
    const val COPY_HISTORY = "copy_history"
    const val VIDEO = "video"
    const val LINK = "link"

    const val ERROR = "error"
    const val ERROR_CODE = "error_code"
    const val ERROR_MSG = "error_msg"
}

object ErrorCodes {
    const val AUTH_FAILED = 5
}

private object Sections {
    const val USERS = "users"
    const val NEWSFEED = "newsfeed"
    const val LIKES = "likes"
}

private object Methods {
    const val GET = "get"
    const val ADD = "add"
    const val DELETE = "delete"
}

private const val API_VERSION = "5.73"

interface Api {

    @GET("${Sections.USERS}.${Methods.GET}")
    fun loadProfile(@Query(ApiFields.ACCESS_TOKEN) accessToken: String,
                    @Query(ApiFields.USER_IDS) userId: Long,
                    @Query(ApiFields.FIELDS) fields: String = ApiFields.PHOTO_100,
                    @Query(ApiFields.V) version: String = API_VERSION): LiveData<ApiResponse<JsonResponse<List<ProfileJson>>>>

    @GET("${Sections.NEWSFEED}.${Methods.GET}")
    fun loadNews(@Query(ApiFields.ACCESS_TOKEN) accessToken: String,
                 @Query(ApiFields.FILTERS) filters: String = ApiFields.POST,
                 @Query(ApiFields.COUNT) count: Int,
                 @Query(ApiFields.START_FROM) startFrom: String = "",
                 @Query(ApiFields.V) version: String = API_VERSION): LiveData<ApiResponse<JsonResponse<NewsJson>>>

    @GET("${Sections.LIKES}.${Methods.ADD}")
    fun addLike(@Query(ApiFields.ACCESS_TOKEN) accessToken: String,
                @Query(ApiFields.TYPE) type: String,
                @Query(ApiFields.ITEM_ID) itemId: Long,
                @Query(ApiFields.OWNER_ID) ownerId: Long,
                @Query(ApiFields.V) version: String = API_VERSION): LiveData<ApiResponse<JsonResponse<LikesCountJson>>>

    @GET("${Sections.LIKES}.${Methods.DELETE}")
    fun deleteLike(@Query(ApiFields.ACCESS_TOKEN) accessToken: String,
                   @Query(ApiFields.TYPE) type: String,
                   @Query(ApiFields.ITEM_ID) itemId: Long,
                   @Query(ApiFields.OWNER_ID) ownerId: Long,
                   @Query(ApiFields.V) version: String = API_VERSION): LiveData<ApiResponse<JsonResponse<LikesCountJson>>>
}

// Common
data class ApiResponse<T>(val jsonResponse: T? = null, val error: Throwable? = null)

data class JsonResponse<T>(@SerializedName(ApiFields.RESPONSE) val response: T?,
                           @SerializedName(ApiFields.ERROR) val serverError: ServerJsonError?)

data class ServerJsonError(@SerializedName(ApiFields.ERROR_CODE) val code: Int,
                           @SerializedName(ApiFields.ERROR_MSG) val message: String)


// Profile
data class ProfileJson(@SerializedName(ApiFields.ID) val id: Long,
                       @SerializedName(ApiFields.FIRST_NAME) val firstName: String,
                       @SerializedName(ApiFields.LAST_NAME) val lastName: String,
                       @SerializedName(ApiFields.PHOTO_100) val photo100: String?)


// Group
data class GroupJson(@SerializedName(ApiFields.ID) val id: Long,
                     @SerializedName(ApiFields.NAME) val name: String,
                     @SerializedName(ApiFields.PHOTO_100) val photo100: String?)


// News
data class NewsJson(@SerializedName(ApiFields.ITEMS) val items: List<NewsItemJson>,
                    @SerializedName(ApiFields.PROFILES) val profiles: List<ProfileJson>,
                    @SerializedName(ApiFields.GROUPS) val groups: List<GroupJson>,
                    @SerializedName(ApiFields.NEXT_FROM) val nextFrom: String)

data class CopyHistoryJsonItem(@SerializedName(ApiFields.OWNER_ID) val ownerId: Long,
                               @SerializedName(ApiFields.DATE) val ownerDate: Long)

data class NewsItemJson(@SerializedName(ApiFields.POST_ID) val postId: Long,
                        @SerializedName(ApiFields.TYPE) val type: String,
                        @SerializedName(ApiFields.SOURCE_ID) val sourceId: Long,
                        @SerializedName(ApiFields.COPY_HISTORY) val copyHistory: List<CopyHistoryJsonItem>?,
                        @SerializedName(ApiFields.DATE) val date: Long,
                        @SerializedName(ApiFields.TEXT) val text: String?,
                        @SerializedName(ApiFields.COMMENTS) val comments: CommentsJson,
                        @SerializedName(ApiFields.LIKES) val likes: LikesJson,
                        @SerializedName(ApiFields.REPOSTS) val reposts: RepostsJson,
                        @SerializedName(ApiFields.VIEWS) val views: ViewsJson,
                        @SerializedName(ApiFields.ATTACHMENTS) val attachments: List<AttachmentJson>?)

data class CommentsJson(@SerializedName(ApiFields.COUNT) val count: Int,
                        @SerializedName(ApiFields.CAN_POST) val canPost: Int)

data class LikesJson(@SerializedName(ApiFields.COUNT) val count: Int,
                     @SerializedName(ApiFields.USER_LIKES) val userLikes: Int,
                     @SerializedName(ApiFields.CAN_LIKE) val canLike: Int,
                     @SerializedName(ApiFields.CAN_PUBLISH) val canPublish: Int)

data class RepostsJson(@SerializedName(ApiFields.COUNT) val count: Int,
                       @SerializedName(ApiFields.USER_REPOSTED) val userReposted: Int)

data class ViewsJson(@SerializedName(ApiFields.COUNT) val count: Int)


// Attachments
data class AttachmentJson(@SerializedName(ApiFields.TYPE) val type: String,
                          @SerializedName(ApiFields.PHOTO) val photo: PhotoJson? = null,
                          @SerializedName(ApiFields.VIDEO) val video: VideoJson? = null,
                          @SerializedName(ApiFields.LINK) val link: LinkJson? = null)

data class PhotoJson(@SerializedName(ApiFields.ID) val id: Long,
                     @SerializedName(ApiFields.PHOTO_130) val photo130: String?,
                     @SerializedName(ApiFields.PHOTO_604) val photo604: String?,
                     @SerializedName(ApiFields.PHOTO_807) val photo807: String?,
                     @SerializedName(ApiFields.PHOTO_1280) val photo1280: String?,
                     @SerializedName(ApiFields.PHOTO_2560) val photo2560: String?,
                     @SerializedName(ApiFields.WIDTH) val width: Int,
                     @SerializedName(ApiFields.HEIGHT) val height: Int)

data class VideoJson(@SerializedName(ApiFields.ID) val id: Long,
                     @SerializedName(ApiFields.PHOTO_130) val photo130: String?,
                     @SerializedName(ApiFields.PHOTO_320) val photo320: String?,
                     @SerializedName(ApiFields.PHOTO_640) val photo640: String?,
                     @SerializedName(ApiFields.PHOTO_800) val photo800: String?,
                     @SerializedName(ApiFields.WIDTH) val width: Int,
                     @SerializedName(ApiFields.HEIGHT) val height: Int)

data class LinkJson(@SerializedName(ApiFields.PHOTO) val photo: PhotoJson)

data class LikesCountJson(@SerializedName(ApiFields.LIKES) val likesCount: Int)
