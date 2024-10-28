package com.example.ecommerceapp.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.domain.model.CartProduct
import com.example.ecommerceapp.navigation.Success
import com.example.ecommerceapp.presentation.components.CartItem
import com.example.ecommerceapp.presentation.components.LoadingView
import com.example.ecommerceapp.util.toAmount
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val product = viewModel.product.collectAsState()
    val onError = viewModel.onError.collectAsState()
    val onLoading = viewModel.onLoading.collectAsState()
    val needRefreshState = viewModel.refreshState.collectAsState()
    val checkOutSuccess = viewModel.checkOutSuccess.collectAsState()
    val selectedList = remember { mutableStateListOf<CartProduct>() }
    var showDelete by remember { mutableStateOf(false) }
    var totalPrice by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(Unit) {
        viewModel.getAllItems()
    }

    LaunchedEffect(selectedList.size) {
        showDelete = !selectedList.isEmpty()
    }

    LaunchedEffect(needRefreshState.value) {
        if (needRefreshState.value) {
            selectedList.clear()
            totalPrice = 0.0
            viewModel.getAllItems()
        }
    }

    LaunchedEffect(checkOutSuccess.value) {
        if (checkOutSuccess.value) {
            navController.navigate(Success)
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Cart") },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (showDelete) {
                        IconButton(onClick = {
                            viewModel.deleteItemFromCart(selectedList)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f),
                    text = "Total : ${totalPrice.toAmount()}"
                )
                Button(onClick = {
                    viewModel.checkOut(selectedList)
                }, enabled = selectedList.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = "Check Out",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
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
                        duration = SnackbarDuration.Long
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                items(product.value) {
                    CartItem(it, onItemSelected = { selectedItem ->
                        val itemPrice = selectedItem.price * selectedItem.quantity
                        if (selectedItem.isSelected) {
                            selectedList.add(selectedItem)
                            totalPrice += itemPrice
                        } else {
                            selectedList.removeIf { item -> item.id == selectedItem.id }
                            totalPrice -= itemPrice
                        }
                    })
                }
            }
        }
    }
}