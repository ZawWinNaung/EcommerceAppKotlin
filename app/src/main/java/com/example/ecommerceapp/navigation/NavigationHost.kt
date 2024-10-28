package com.example.ecommerceapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ecommerceapp.presentation.cart.CartScreen
import com.example.ecommerceapp.presentation.check_out.SuccessScreen
import com.example.ecommerceapp.presentation.detail.DetailScreen
import com.example.ecommerceapp.presentation.home.HomeScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(navController)
        }
        composable<Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<Detail>()
            DetailScreen(navController, args.id)
        }
        composable<Cart> {
            CartScreen(navController)
        }
        composable<Success> {
            SuccessScreen(navController)
        }
    }

}