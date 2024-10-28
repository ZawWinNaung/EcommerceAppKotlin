package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.repo.ApiRepository
import com.example.core.domain.core.MyResult
import com.example.core.domain.core.Usecase
import com.example.core.domain.model.Product
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repo: ApiRepository
) : Usecase<Int, Product?>() {
    override suspend fun provide(input: Int): MyResult<Product?> {
        return try {
            val result = repo.getProductById(input)
            if (result.isSuccess) {
                MyResult.Success(result.getOrNull())
            } else {
                MyResult.Error(result.exceptionOrNull())
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}