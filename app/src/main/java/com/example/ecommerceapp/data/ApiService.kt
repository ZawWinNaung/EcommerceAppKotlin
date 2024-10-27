package com.example.ecommerceapp.data

import com.example.ecommerceapp.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    suspend fun getProducts(): Response<List<Product>>

//    @GET("data/obs/{regionCode}/recent")
//    suspend fun getRecentObservations(
//        @Path("regionCode") regionCode: String
//    ): Response<List<Bird>>
}