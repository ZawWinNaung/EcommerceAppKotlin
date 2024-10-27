package com.example.ecommerceapp.data.api

import com.example.ecommerceapp.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<Product>
}