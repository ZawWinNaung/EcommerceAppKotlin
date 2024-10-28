package com.example.database.domain.usecase

import com.example.database.data.repo.CartItemRepository
import com.example.core.domain.core.MyResult
import com.example.core.domain.core.Usecase
import com.example.database.domain.mapper.ProductEntityMapper
import com.example.core.domain.model.CartProduct
import javax.inject.Inject

class GetAllCartItemsUseCase @Inject constructor(
    private val repo: CartItemRepository
) : Usecase<Boolean, List<CartProduct>>() {
    override suspend fun provide(input: Boolean): MyResult<List<CartProduct>> {
        return try {
            val list = repo.getCartItem(input)
            MyResult.Success(ProductEntityMapper.map(list))
        } catch (e: Throwable) {
            MyResult.Error(e)
        }
    }
}