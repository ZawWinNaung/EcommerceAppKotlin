package com.example.ecommerceapp.data.repo

import com.example.core.domain.model.Product

interface ApiRepository {
    suspend fun getProducts(): Result<List<Product>>

    suspend fun getProductById(id:Int): Result<Product?>
}