package com.example.ecommerceapp.presentation.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Text("cart screen")
        }
    }
}