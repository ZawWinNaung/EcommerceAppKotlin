package com.example.ecommerceapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.core.ApiResult
import com.example.ecommerceapp.domain.model.Product
import com.example.ecommerceapp.domain.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _onError = MutableStateFlow<Throwable?>(null)
    val onError: StateFlow<Throwable?> = _onError

    private val _onLoading = MutableStateFlow(false)
    val onLoading: StateFlow<Boolean> = _onLoading

    fun getProductById(id: Int) {
        _onLoading.value = true
        viewModelScope.launch {
            when (val result = getProductByIdUseCase.execute(id)) {
                is ApiResult.Success -> {
                    _onLoading.value = false
                    _product.value = result.data
                }

                is ApiResult.Error -> {
                    _onLoading.value = false
                    _onError.value = result.throwable
                }
            }
        }
    }
}