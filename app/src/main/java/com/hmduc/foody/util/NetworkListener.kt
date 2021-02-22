package com.hmduc.foody.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener: ConnectivityManager.NetworkCallback() {

    private var isNetWorkAvaiable = MutableStateFlow(false)

    fun checkNetworkAvaiability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false

        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }

        isNetWorkAvaiable.value = isConnected

        return isNetWorkAvaiable
    }

    override fun onAvailable(network: Network) {
        isNetWorkAvaiable.value = true
    }

    override fun onLost(network: Network) {
        isNetWorkAvaiable.value = false
    }
}