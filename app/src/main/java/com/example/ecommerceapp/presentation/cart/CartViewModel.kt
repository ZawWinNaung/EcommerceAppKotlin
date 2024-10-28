package com.example.ecommerceapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.core.MyResult
import com.example.ecommerceapp.domain.model.CartProduct
import com.example.ecommerceapp.domain.usecase.CheckOutUseCase
import com.example.ecommerceapp.domain.usecase.DeleteItemByIdUseCase
import com.example.ecommerceapp.domain.usecase.GetAllCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val deleteItemByIdUseCase: DeleteItemByIdUseCase,
    private val checkOutUseCase: CheckOutUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<List<CartProduct>>(emptyList())
    val product: StateFlow<List<CartProduct>> = _product

    private val _onError = MutableStateFlow<Throwable?>(null)
    val onError: StateFlow<Throwable?> = _onError

    private val _onLoading = MutableStateFlow(false)
    val onLoading: StateFlow<Boolean> = _onLoading

    private val _refreshState = MutableStateFlow(false)
    val refreshState: StateFlow<Boolean> = _refreshState

    private val _checkOutSuccess = MutableStateFlow(false)
    val checkOutSuccess: StateFlow<Boolean> = _checkOutSuccess

    fun getAllItems() {
        _refreshState.value = false
        _onLoading.value = true
        viewModelScope.launch {
            when (val result = getAllCartItemsUseCase.execute(false)) {
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

    fun deleteItemFromCart(itemList: List<CartProduct>) {
        viewModelScope.launch {
            when (val result = deleteItemByIdUseCase.execute(itemList)) {
                is MyResult.Error -> {}
                is MyResult.Success -> {
                    _refreshState.value = true
                }
            }
        }
    }

    fun checkOut(itemList: List<CartProduct>) {
        viewModelScope.launch {
            when (val result = checkOutUseCase.execute(itemList)) {
                is MyResult.Error -> {}
                is MyResult.Success -> {
                    _refreshState.value = true
                    _checkOutSuccess.value = true
                }
            }
        }
    }
}