package com.example.ecommerceapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.model.CartProduct

@Composable
fun CartItem(
    product: CartProduct,
    onItemSelected: (CartProduct) -> Unit,
) {

    var checkState by remember { mutableStateOf(product.isSelected) }

    LaunchedEffect(product.isSelected) {
        checkState = product.isSelected
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clickable {
            checkState = !checkState
            onItemSelected(product.apply { isSelected = !isSelected })
        }) {

        Row {
            Checkbox(
                checked = checkState,
                onCheckedChange = {
                    checkState = it
                    onItemSelected(product.apply { isSelected = it })
                },
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.rounded_image_24),
                contentScale = ContentScale.Fit,
                contentDescription = product.description,
                modifier = Modifier.size(80.dp)
            )
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    text = product.title
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                    text = "price : ${product.price * product.quantity}$ x ${product.quantity}",
                )
            }
        }

    }
}