package com.example.core.domain.core

sealed class MyResult<out T> {
    data class Success<out T>(val data: T) : MyResult<T>()
    data class Error(val throwable: Throwable?) : MyResult<Nothing>()
}