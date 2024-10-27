package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.api.repo.ApiRepository
import com.example.ecommerceapp.domain.core.MyResult
import com.example.ecommerceapp.domain.core.Usecase
import com.example.ecommerceapp.domain.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ApiRepository
): Usecase<Unit, List<Product>>() {
    override suspend fun provide(input: Unit): MyResult<List<Product>> {
        return try {
            val result = repo.getProducts()
            if (result.isSuccess) {
                MyResult.Success(result.getOrNull() ?: emptyList())
            } else {
                MyResult.Error(result.exceptionOrNull())
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}