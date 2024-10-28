package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.cache.repo.CartItemRepository
import com.example.ecommerceapp.domain.core.MyResult
import com.example.ecommerceapp.domain.core.Usecase
import javax.inject.Inject

class GetCartItemCount @Inject constructor(
    private val repo: CartItemRepository
) : Usecase<Unit, Int>() {
    override suspend fun provide(input: Unit): MyResult<Int> {
        return try {
            val count = repo.getCartItem(false).size
            MyResult.Success(count)
        } catch (e: Throwable) {
            MyResult.Error(e)
        }
    }
}