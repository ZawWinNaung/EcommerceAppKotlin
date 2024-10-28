package com.example.database.domain.usecase

import com.example.database.data.repo.CartItemRepository
import com.example.core.domain.core.MyResult
import com.example.core.domain.core.Usecase
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