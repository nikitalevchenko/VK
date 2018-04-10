package com.example.nikitalevcenko.vk.api

import android.arch.lifecycle.LiveData
import com.example.nikitalevcenko.vk.entity.Profile
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

    const val ERROR = "serverError"
    const val ERROR_CODE = "error_code"
    const val ERROR_MSG = "error_msg"
}

object ErrorCodes {
    const val AUTH_FAILED = 5
}

private object Sections {
    const val USERS = "users"
}

private object Methods {
    const val GET = "get"
}

private const val API_VERSION = "5.73"

interface Api {

    @GET("${Sections.USERS}.${Methods.GET}")
    fun loadProfile(@Query(ApiFields.ACCESS_TOKEN) accessToken: String,
                    @Query(ApiFields.USER_IDS) userId: Long,
                    @Query(ApiFields.FIELDS) fields: String = ApiFields.PHOTO_100,
                    @Query(ApiFields.V) version: String = API_VERSION): LiveData<ApiResponse<JsonResponse<List<Profile>>>>
}

data class ApiResponse<T>(val jsonResponse: T? = null, val error: Throwable? = null)

data class JsonResponse<T>(
        @SerializedName(ApiFields.RESPONSE) val response: T?,
        @SerializedName(ApiFields.ERROR) val serverError: ServerJsonError? = null
)

data class ServerJsonError(@SerializedName(ApiFields.ERROR_CODE) val code: Int,
                           @SerializedName(ApiFields.ERROR_MSG) val message: String)
