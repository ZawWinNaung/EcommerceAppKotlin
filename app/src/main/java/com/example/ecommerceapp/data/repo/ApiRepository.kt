package com.example.ecommerceapp.data.repo

import com.example.ecommerceapp.domain.model.Product

interface ApiRepository {
    suspend fun getProducts(): Result<List<Product>>
}