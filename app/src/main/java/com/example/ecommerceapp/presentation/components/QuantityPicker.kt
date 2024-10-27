package com.example.ecommerceapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuantityPicker(onChange: (Int) -> Unit) {
    var quantity by remember { mutableIntStateOf(1) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Minus button
        Button(
            onClick = {
                if (quantity > 1) {
                    quantity--
                    onChange(quantity)
                }
            },
            enabled = quantity > 1
        ) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = ""
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = {
                quantity++
                onChange(quantity)
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = ""
            )
        }
    }
}
