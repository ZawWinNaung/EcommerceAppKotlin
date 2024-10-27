package com.example.ecommerceapp.domain.core

abstract class Usecase<I, O> {

    suspend fun execute(input: I): ApiResult<O> {
        return try {
            provide(input)
        } catch (e: Throwable) {
            handleError(e)
        }
    }

    abstract suspend fun provide(input: I): ApiResult<O>

    protected open suspend fun handleError(e: Throwable): ApiResult<O> {
        return ApiResult.Error(e)
    }
}