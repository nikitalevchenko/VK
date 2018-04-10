package com.example.nikitalevcenko.vk.repo

import com.example.nikitalevcenko.vk.api.ApiResponse
import com.example.nikitalevcenko.vk.api.ErrorCodes
import com.example.nikitalevcenko.vk.api.JsonResponse
import com.example.nikitalevcenko.vk.api.ServerJsonError
import com.example.nikitalevcenko.vk.exceptions.AccessTokenExpiredException
import com.example.nikitalevcenko.vk.exceptions.ServerError

interface ApiResponseHandler {

    fun <T> handleApiResponse(response: ApiResponse<JsonResponse<T>>?,
                              onSuccess: (data: T) -> Unit): NetworkState {

        if (response?.error != null) {
            return NetworkState.error(response.error)
        } else if (response?.jsonResponse != null) {

            if (response.jsonResponse.serverError != null) {
                return handleServerError(response.jsonResponse.serverError)
            } else if (response.jsonResponse.response != null) {
                onSuccess.invoke(response.jsonResponse.response)
                return NetworkState.LOADED
            }
        }

        return NetworkState.error(ServerError("Empty response"))
    }

    fun handleServerError(error: ServerJsonError): NetworkState {
        return when (error.code) {
            ErrorCodes.AUTH_FAILED -> NetworkState.error(AccessTokenExpiredException())
            else -> NetworkState.error(ServerError(error.message))
        }
    }
}