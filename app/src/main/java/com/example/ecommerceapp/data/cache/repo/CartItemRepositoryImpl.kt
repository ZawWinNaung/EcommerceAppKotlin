package com.example.ecommerceapp.data.cache.repo

import com.example.ecommerceapp.data.cache.CartItemDao
import com.example.ecommerceapp.domain.entity.CartItemEntity
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val dao: CartItemDao
) : CartItemRepository {
    override suspend fun getCartItem(): List<CartItemEntity> {
        return dao.getCartItems()
    }

    override suspend fun insertItem(item: CartItemEntity) {
        dao.insertItem(item)
    }
}