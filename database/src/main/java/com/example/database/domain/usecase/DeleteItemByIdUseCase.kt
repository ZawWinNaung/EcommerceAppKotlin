package com.example.database.domain.usecase

import com.example.database.data.repo.CartItemRepository
import com.example.core.domain.core.MyResult
import com.example.core.domain.core.Usecase
import com.example.core.domain.model.CartProduct
import javax.inject.Inject

class DeleteItemByIdUseCase @Inject constructor(
    private val repo: CartItemRepository
) : Usecase<List<CartProduct>, Unit>() {
    override suspend fun provide(input: List<CartProduct>): MyResult<Unit> {
        return try {
            input.forEach {
                repo.deleteItem(it.id)
            }
            MyResult.Success(Unit)
        } catch (e: Throwable) {
            MyResult.Error(e)
        }
    }
}