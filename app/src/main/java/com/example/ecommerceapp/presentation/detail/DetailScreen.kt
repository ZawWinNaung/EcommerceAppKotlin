package com.example.ecommerceapp.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerceapp.R
import com.example.ecommerceapp.navigation.Cart
import com.example.ecommerceapp.presentation.components.AddToCartBottomSheet
import com.example.ecommerceapp.presentation.components.LoadingView
import com.example.ecommerceapp.presentation.components.QuantityPicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController, args: Int, viewModel: DetailViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val product = viewModel.product.collectAsState()
    val onError = viewModel.onError.collectAsState()
    val onLoading = viewModel.onLoading.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var itemCount by remember { mutableIntStateOf(1) }
    val onItemInsertSuccess by viewModel.insertItemSuccess

    LaunchedEffect(true) {
        viewModel.getProductById(args)
    }

    LaunchedEffect(onItemInsertSuccess) {
        if (onItemInsertSuccess) {
            navController.navigate(Cart)
            viewModel.resetSuccess()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Detail") }, navigationIcon = {
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
                .verticalScroll(scrollState)
        ) {
            if (onLoading.value) {
                LoadingView()
            }

            product.value?.let { product ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(product.image)
                        .crossfade(true).build(),
                    placeholder = painterResource(R.drawable.rounded_image_24),
                    contentScale = ContentScale.Fit,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .weight(1f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    product.title?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            text = it,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        product.price?.let {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = "price : $it$",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        product.rating?.let {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = "Rating : ${it.rate} by ${it.count} users",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    product.description?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp), onClick = {
                        showBottomSheet = true
                    }) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            text = "Add to cart",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }

            onError.value?.let { error ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = error.message ?: "Unknown Error",
                        duration = SnackbarDuration.Long
                    )
                }
            }
            if (showBottomSheet) {
                AddToCartBottomSheet(
                    onButtonClick = {
                        product.value?.let {
                            viewModel.insertItemToCart(it, itemCount)
                        }
                    },
                    onDismiss = { showBottomSheet = false }
                ) {
                    product.value?.price?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "price : ${it * itemCount}$ x $itemCount",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        QuantityPicker(
                            onChange = {
                                itemCount = it
                            }
                        )
                    }
                }
            }
        }
    }
}