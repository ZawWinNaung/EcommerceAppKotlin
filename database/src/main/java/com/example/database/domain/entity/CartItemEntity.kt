package com.example.database.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_item")
data class CartItemEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("count")
    val count: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("is_check_out")
    val isCheckOut: Boolean
)