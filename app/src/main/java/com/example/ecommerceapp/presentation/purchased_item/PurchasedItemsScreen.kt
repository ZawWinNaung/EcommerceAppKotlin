package com.example.ecommerceapp.presentation.purchased_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.navigation.Detail
import com.example.ecommerceapp.presentation.components.CartItem
import com.example.ecommerceapp.presentation.components.LoadingView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasedItemsScreen(
    navController: NavController,
    viewModel: PurchasedItemsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val product = viewModel.product.collectAsState()
    val onError = viewModel.onError.collectAsState()
    val onLoading = viewModel.onLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPurchasedItems()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Purchased") }, navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (onLoading.value) {
                LoadingView()
            }
            onError.value?.let { error ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = error.message ?: "Unknown Error",
                        duration = SnackbarDuration.Short
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                items(product.value) {
                    CartItem(it, showCheckBox = false, onItemSelected = { item ->
                        navController.navigate(Detail(id = item.productId))
                    })
                }
            }
        }
    }
}