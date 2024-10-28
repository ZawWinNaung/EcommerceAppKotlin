package com.example.ecommerceapp.domain.model

data class CartProduct(
    val id: Int,
    val productId: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
    val quantity: Int,
    var isSelected: Boolean = false,
    var isCheckOut: Boolean = false
)