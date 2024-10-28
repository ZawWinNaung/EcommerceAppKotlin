package com.example.ecommerceapp.presentation.purchased_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.core.MyResult
import com.example.core.domain.model.CartProduct
import com.example.database.domain.usecase.GetAllCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasedItemsViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase
) : ViewModel() {
    private val _product = MutableStateFlow<List<CartProduct>>(emptyList())
    val product: StateFlow<List<CartProduct>> = _product

    private val _onError = MutableStateFlow<Throwable?>(null)
    val onError: StateFlow<Throwable?> = _onError

    private val _onLoading = MutableStateFlow(false)
    val onLoading: StateFlow<Boolean> = _onLoading

    fun getPurchasedItems() {
        _onLoading.value = true
        viewModelScope.launch {
            when (val result = getAllCartItemsUseCase.execute(true)) {
                is MyResult.Success -> {
                    _onLoading.value = false
                    _product.value = result.data.reversed()
                }

                is MyResult.Error -> {
                    _onLoading.value = false
                    _onError.value = result.throwable
                }
            }
        }
    }
}