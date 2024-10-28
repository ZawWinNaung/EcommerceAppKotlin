package com.example.ecommerceapp.data.cache.repo

import com.example.ecommerceapp.domain.entity.CartItemEntity

interface CartItemRepository {
    suspend fun getCartItem(isCheckOut: Boolean): List<CartItemEntity>

    suspend fun insertItem(item: CartItemEntity)

    suspend fun deleteItem(id: Int)

    suspend fun updateItem(id: Int, isCheckOut: Boolean)
}