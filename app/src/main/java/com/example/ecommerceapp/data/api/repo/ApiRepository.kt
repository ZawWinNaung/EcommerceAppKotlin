package com.example.ecommerceapp.data.api.repo

import com.example.ecommerceapp.domain.model.Product

interface ApiRepository {
    suspend fun getProducts(): Result<List<Product>>

    suspend fun getProductById(id:Int): Result<Product?>
}