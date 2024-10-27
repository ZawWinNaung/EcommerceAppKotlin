package com.example.ecommerceapp.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.domain.entity.CartItemEntity

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    suspend fun getCartItems(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(event:CartItemEntity)
}