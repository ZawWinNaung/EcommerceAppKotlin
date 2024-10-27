package com.example.ecommerceapp.domain.usecase

import com.example.ecommerceapp.data.cache.repo.CartItemRepository
import com.example.ecommerceapp.domain.core.MyResult
import com.example.ecommerceapp.domain.core.Usecase
import com.example.ecommerceapp.domain.entity.CartItemEntity
import com.example.ecommerceapp.domain.model.Product
import javax.inject.Inject

class InsertItemToCartUseCase @Inject constructor(
    private val repo: CartItemRepository
) :
    Usecase<InsertItemToCartUseCase.Input, Unit>() {

    override suspend fun provide(input: Input): MyResult<Unit> {
        return try {
            val product = input.product
            val entityItem = CartItemEntity(
                id = 0,
                title = product.title ?: "",
                price = product.price ?: 0.0,
                description = product.description ?: "",
                category = product.category ?: "",
                image = product.image ?: "",
                rate = product.rating?.rate ?: 0.0,
                count = product.rating?.count ?: 0,
                quantity = input.quantity
            )
            repo.insertItem(entityItem)
            MyResult.Success(Unit)
        } catch (e: Throwable) {
            MyResult.Error(e)
        }
    }

    data class Input(
        val product: Product,
        val quantity: Int
    )
}

