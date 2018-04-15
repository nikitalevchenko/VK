package com.example.nikitalevcenko.vk.repo

import android.util.Log
import com.example.nikitalevcenko.vk.api.ApiResponse
import com.example.nikitalevcenko.vk.api.ErrorCodes
import com.example.nikitalevcenko.vk.api.JsonResponse
import com.example.nikitalevcenko.vk.api.ServerJsonError
import com.example.nikitalevcenko.vk.exceptions.AccessTokenExpiredException
import com.example.nikitalevcenko.vk.exceptions.ServerError

interface ApiResponseHandler {

//    fun <T> handleApiResponse(response: ApiResponse<JsonResponse<T>>?,
//                              onSuccess: (data: T) -> Unit): NetworkState {
//
//        if (response?.error != null) {
//            return NetworkState.error(response.error)
//        } else if (response?.jsonResponse != null) {
//
//            if (response.jsonResponse.serverError != null) {
//                return handleServerError(response.jsonResponse.serverError)
//            } else if (response.jsonResponse.response != null) {
//                onSuccess(response.jsonResponse.response)
//                return NetworkState.LOADED
//            }
//        }
//
//        return NetworkState.error(ServerError("Empty response"))
//    }

    fun <T, D> handleApiResponse(response: ApiResponse<JsonResponse<T>>?,
                                 map: (data: T) -> D = { data -> data as D },
                                 onSuccess: (data: D) -> Unit = {},
                                 onError: (error: Throwable) -> Unit = {}): NetworkState {

        if (response?.error != null) {
            onError(response.error)
            return NetworkState.error(response.error)
        } else if (response?.jsonResponse != null) {

            if (response.jsonResponse.serverError != null) {
                return handleServerError(response.jsonResponse.serverError, onError)
            } else if (response.jsonResponse.response != null) {
                onSuccess(map(response.jsonResponse.response))
                return NetworkState.LOADED
            }
        }

        return NetworkState.error(ServerError("Empty response"))
    }

    fun handleServerError(error: ServerJsonError, onError: (error: Throwable) -> Unit = {}): NetworkState {

        val throwable = mapError(error)

        onError(throwable)

        return NetworkState.error(throwable)
    }

    private fun mapError(error: ServerJsonError): Throwable {
        return when (error.code) {
            ErrorCodes.AUTH_FAILED -> {
                AccessTokenExpiredException()
            }
            else -> {
                ServerError(error.message)
            }
        }
    }
}