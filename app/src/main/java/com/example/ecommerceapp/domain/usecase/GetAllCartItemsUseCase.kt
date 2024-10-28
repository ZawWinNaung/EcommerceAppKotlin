package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.cache.repo.CartItemRepository
import com.example.ecommerceapp.domain.core.MyResult
import com.example.ecommerceapp.domain.core.Usecase
import com.example.ecommerceapp.domain.mapper.ProductEntityMapper
import com.example.ecommerceapp.domain.model.CartProduct
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