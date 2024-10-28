package com.example.ecommerceapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkConnectivityObserver(private val context: Context) {
    private val _connectivityFlow = MutableStateFlow(isInternetAvailable(context))
    val connectivityFlow = _connectivityFlow.asStateFlow()

    fun observe(onConnectionChanged: (Boolean) -> Unit) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                _connectivityFlow.value = true
                onConnectionChanged(true)
            }

            override fun onLost(network: android.net.Network) {
                _connectivityFlow.value = false
                onConnectionChanged(false)
            }
        })
    }
}