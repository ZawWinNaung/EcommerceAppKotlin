package com.example.database.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.domain.entity.CartItemEntity

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item WHERE isCheckOut = :isCheckOut")
    suspend fun getCartItems(isCheckOut: Boolean): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(event: CartItemEntity)

    @Query("DELETE FROM cart_item WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE cart_item SET isCheckOut = :isCheckOut WHERE id = :id")
    suspend fun updateItem(id: Int, isCheckOut: Boolean)
}