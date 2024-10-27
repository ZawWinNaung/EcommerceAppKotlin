package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.repo.ApiRepository
import com.example.ecommerceapp.domain.core.ApiResult
import com.example.ecommerceapp.domain.core.Usecase
import com.example.ecommerceapp.domain.model.Product
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repo: ApiRepository
) : Usecase<Int, Product?>() {
    override suspend fun provide(input: Int): ApiResult<Product?> {
        return try {
            val result = repo.getProductById(input)
            if (result.isSuccess) {
                ApiResult.Success(result.getOrNull())
            } else {
                ApiResult.Error(result.exceptionOrNull())
            }
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}