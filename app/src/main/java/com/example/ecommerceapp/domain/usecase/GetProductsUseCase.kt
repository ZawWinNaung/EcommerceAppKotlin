package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.repo.ApiRepository
import com.example.ecommerceapp.domain.core.ApiResult
import com.example.ecommerceapp.domain.core.Usecase
import com.example.ecommerceapp.domain.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ApiRepository
): Usecase<Unit, List<Product>>() {
    override suspend fun provide(input: Unit): ApiResult<List<Product>> {
        return try {
            val result = repo.getProducts()
            if (result.isSuccess) {
                ApiResult.Success(result.getOrNull() ?: emptyList())
            } else {
                ApiResult.Error(result.exceptionOrNull())
            }
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}