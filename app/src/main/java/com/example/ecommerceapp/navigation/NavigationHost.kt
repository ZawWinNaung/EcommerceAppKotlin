package com.example.ecommerceapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.presentation.detail.DetailScreen
import com.example.ecommerceapp.presentation.home.HomeScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(navController)
        }
        composable<Detail> {
            DetailScreen(navController)
        }
    }

}