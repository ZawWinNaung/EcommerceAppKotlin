package com.example.database.data.repo

import com.example.database.data.CartItemDao
import com.example.database.domain.entity.CartItemEntity
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val dao: CartItemDao
) : CartItemRepository {
    override suspend fun getCartItem(isCheckOut: Boolean): List<CartItemEntity> {
        return dao.getCartItems(isCheckOut)
    }

    override suspend fun insertItem(item: CartItemEntity) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun updateItem(id: Int, isCheckOut: Boolean) {
        dao.updateItem(id, isCheckOut)
    }
}