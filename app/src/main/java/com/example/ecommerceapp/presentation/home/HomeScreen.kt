package com.example.ecommerceapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.navigation.Cart
import com.example.ecommerceapp.navigation.Detail
import com.example.ecommerceapp.presentation.components.LoadingView
import com.example.ecommerceapp.presentation.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyStaggeredGridState()
    val products = viewModel.products.collectAsState()
    val onError = viewModel.onError.collectAsState()
    val onLoading = viewModel.onLoading.collectAsState()
    val cartItemCount = viewModel.cartItemCount.collectAsState()
    LaunchedEffect(true) {
        viewModel.getProducts()
        viewModel.getItemCount()
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "Home") }, actions = {
            IconButton(onClick = { navController.navigate(Cart) }) {
                BadgedBox(
                    badge = {
                        if (cartItemCount.value > 0) {
                            Badge { Text(cartItemCount.value.toString()) }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart"
                    )
                }
            }
        })
    }) { paddings ->
        Column(modifier = Modifier.padding(paddings)) {
            if (onLoading.value) {
                LoadingView()
            }
            if (products.value.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    columns = StaggeredGridCells.Adaptive(200.dp),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(products.value) { product ->
                            ProductItem(product) {
                                navController.navigate(Detail(it))
                            }
                        }
                    },
                )
            }
            onError.value?.let {
//                ErrorScreen(error = it)
            }
        }
    }
}