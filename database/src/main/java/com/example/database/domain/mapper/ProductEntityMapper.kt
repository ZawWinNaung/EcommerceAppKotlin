package com.example.database.domain.mapper

import com.example.database.domain.entity.CartItemEntity
import com.example.core.domain.model.CartProduct
import com.example.core.domain.model.Rating

object ProductEntityMapper {
    val map: (List<CartItemEntity>) -> (List<CartProduct>) = { list ->
        val productList: MutableList<CartProduct> = mutableListOf()
        list.forEach {
            productList.add(
                CartProduct(
                    id = it.id,
                    productId = it.productId,
                    title = it.title,
                    price = it.price,
                    description = it.description,
                    category = it.category,
                    image = it.image,
                    rating = Rating(it.rate, it.count),
                    quantity = it.quantity
                )
            )
        }
        productList
    }
}