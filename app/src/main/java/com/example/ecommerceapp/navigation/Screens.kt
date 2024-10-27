package com.example.ecommerceapp.navigation

import com.example.ecommerceapp.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data class Detail(
    val id: Int
)