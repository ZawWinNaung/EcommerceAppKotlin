package com.example.ecommerceapp.data.cache.repo

import com.example.ecommerceapp.domain.entity.CartItemEntity

interface CartItemRepository {
    suspend fun getCartItem(): List<CartItemEntity>

    suspend fun insertItem(item: CartItemEntity)
}