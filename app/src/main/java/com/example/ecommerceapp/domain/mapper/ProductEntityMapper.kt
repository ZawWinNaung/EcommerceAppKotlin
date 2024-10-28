package com.example.ecommerceapp.domain.mapper

import com.example.ecommerceapp.domain.entity.CartItemEntity
import com.example.ecommerceapp.domain.model.CartProduct
import com.example.ecommerceapp.domain.model.Rating

object ProductEntityMapper {
    val map: (List<CartItemEntity>) -> (List<CartProduct>) = { list ->
        val productList: MutableList<CartProduct> = mutableListOf()
        list.forEach {
            if (!it.isCheckOut) {
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
        }
        productList
    }
}