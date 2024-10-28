package com.example.ecommerceapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerceapp.R
import com.example.core.domain.model.Product

@Composable
fun ProductItem(
    product: Product,
    onItemClick: (Int) -> Unit
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .clickable {
            onItemClick(product.id ?: 0)
        }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.rounded_image_24),
            contentScale = ContentScale.Crop,
            contentDescription = product.description,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        product.title?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                text = it
            )
        }

        product.price?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                text = "price : $it$"
            )
        }
    }
}