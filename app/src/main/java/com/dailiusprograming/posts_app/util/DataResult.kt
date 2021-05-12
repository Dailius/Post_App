package com.dailiusprograming.posts_app.util

import com.dailiusprograming.posts_app.data.model.Error

data class DataResult<out T>(
    val status: Status,
    val data: T?,
    val error: Error?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        INIT,
        REFRESH
    }

    companion object {
        fun <T> success(data: T?): DataResult<T> {
            return DataResult(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): DataResult<T> {
            return DataResult(Status.ERROR, null, error , message)
        }

        fun <T> loading(data: T? = null): DataResult<T> {
            return DataResult(Status.LOADING, data, null, null)
        }

        fun <T> init(data: T? = null): DataResult<T> {
            return DataResult(Status.INIT, null, null, null)
        }

        fun <T> refresh(data: T? = null): DataResult<T> {
            return DataResult(Status.REFRESH, null, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}
