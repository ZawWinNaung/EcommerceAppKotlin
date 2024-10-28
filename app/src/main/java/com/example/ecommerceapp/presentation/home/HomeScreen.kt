package com.example.ecommerceapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.navigation.Cart
import com.example.ecommerceapp.navigation.Detail
import com.example.ecommerceapp.navigation.Purchased
import com.example.ecommerceapp.presentation.components.LoadingView
import com.example.ecommerceapp.presentation.components.NoInternetMessage
import com.example.ecommerceapp.presentation.components.ProductItem
import com.example.ecommerceapp.util.NetworkConnectivityObserver
import com.example.ecommerceapp.util.isInternetAvailable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyStaggeredGridState()
    val products = viewModel.products.collectAsState()
    val onError = viewModel.onError.collectAsState()
    val onLoading = viewModel.onLoading.collectAsState()
    val cartItemCount = viewModel.cartItemCount.collectAsState()

    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isConnected = isInternetAvailable(context)
    }

    LaunchedEffect(Unit) {
        val connectivityObserver = NetworkConnectivityObserver(context)
        connectivityObserver.observe { connected ->
            isConnected = connected
        }
    }

    if (isConnected) {
        LaunchedEffect(Unit) {
            viewModel.getProducts()
            viewModel.getItemCount()
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text(text = "Home") }, actions = {
                    IconButton(onClick = { navController.navigate(Cart) }) {
                        BadgedBox(
                            modifier = Modifier.padding(end = 8.dp),
                            badge = {
                                if (cartItemCount.value > 0) {
                                    Badge { Text(cartItemCount.value.toString()) }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                    }
                    IconButton(onClick = { navController.navigate(Purchased) }) {
                        Icon(
                            imageVector = Icons.Rounded.ShoppingBag,
                            contentDescription = "Cart"
                        )
                    }
                })
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { paddings ->
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

                onError.value?.let { error ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = error.message ?: "Unknown Error",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    } else {
        NoInternetMessage()
    }
}