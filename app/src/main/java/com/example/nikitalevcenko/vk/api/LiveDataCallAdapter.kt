package com.example.nikitalevcenko.vk.api

import android.arch.lifecycle.LiveData
import com.example.nikitalevcenko.vk.exceptions.ServerError
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            val started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(ApiResponse<R>(null, throwable))
                        }

                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(convert(response))
                        }

                    })
                }
            }
        }
    }


    companion object {
        fun <R> convert(response: Response<R>): ApiResponse<R> {
            if (response.isSuccessful()) {
                return ApiResponse(response.body(), null);
            } else {
                var message: String? = null

                if (response.errorBody() != null) {
                    try {
                        message = response.errorBody()!!.string()
                    } catch (ignored: IOException) {
                        Timber.e(ignored, "Error while parsing response")
                    }

                }

                if (message == null || message.trim().isEmpty()) {
                    message = response.message()
                }

                if (message == null) {
                    message = "Unknown server error"
                }

                return ApiResponse(null, ServerError(message))
            }
        }
    }
}