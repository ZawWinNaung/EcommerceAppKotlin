package com.example.ecommerceapp.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data class Detail(
    val id: Int
)

@Serializable
data object Cart

@Serializable
data object Success

@Serializable
data object Purchased