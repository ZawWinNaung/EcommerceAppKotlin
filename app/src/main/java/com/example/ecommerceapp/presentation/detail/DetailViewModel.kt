package com.example.ecommerceapp.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.core.MyResult
import com.example.core.domain.model.Product
import com.example.ecommerceapp.domain.usecase.GetProductByIdUseCase
import com.example.database.domain.usecase.InsertItemToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val insertItemToCartUseCase: InsertItemToCartUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _onError = MutableStateFlow<Throwable?>(null)
    val onError: StateFlow<Throwable?> = _onError

    private val _onLoading = MutableStateFlow(false)
    val onLoading: StateFlow<Boolean> = _onLoading

    val insertItemSuccess = mutableStateOf(false)

    fun getProductById(id: Int) {
        _onLoading.value = true
        viewModelScope.launch {
            when (val result = getProductByIdUseCase.execute(id)) {
                is MyResult.Success -> {
                    _onLoading.value = false
                    _product.value = result.data
                }

                is MyResult.Error -> {
                    _onLoading.value = false
                    _onError.value = result.throwable
                }
            }
        }
    }

    fun insertItemToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            val result = insertItemToCartUseCase.execute(
                InsertItemToCartUseCase.Input(
                    product, quantity
                )
            )
            when (result) {
                is MyResult.Error -> {
                    _onLoading.value = false
                    _onError.value = result.throwable
                    insertItemSuccess.value = false
                }

                else -> {
                    insertItemSuccess.value = true
                }
            }
        }
    }

    fun resetSuccess() {
        insertItemSuccess.value = false
    }
}