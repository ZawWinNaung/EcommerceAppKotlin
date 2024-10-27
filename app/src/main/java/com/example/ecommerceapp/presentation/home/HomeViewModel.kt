package com.example.ecommerceapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.core.MyResult
import com.example.ecommerceapp.domain.model.Product
import com.example.ecommerceapp.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _onError = MutableStateFlow<Throwable?>(null)
    val onError: StateFlow<Throwable?> = _onError

    private val _onLoading = MutableStateFlow(false)
    val onLoading: StateFlow<Boolean> = _onLoading

    fun getProducts() {
        _onLoading.value = true
        viewModelScope.launch {
            when (val result = getProductsUseCase.execute(Unit)) {
                is MyResult.Success -> {
                    _onLoading.value = false
                    _products.value = result.data
                }

                is MyResult.Error -> {
                    _onLoading.value = false
                    _onError.value = result.throwable
                }
            }
        }
    }
}