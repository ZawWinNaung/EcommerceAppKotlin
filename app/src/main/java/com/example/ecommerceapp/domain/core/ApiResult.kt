package com.example.ecommerceapp.domain.core

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val throwable: Throwable?) : ApiResult<Nothing>()
}