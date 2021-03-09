package com.userfaltakas.movieapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.userfaltakas.movieapp.data.enums.NetworkState

class NetworkListener : ConnectivityManager.NetworkCallback() {
    private var networkState = NetworkState.DISCONNECTED

    fun checkNetworkAvailability(context: Context): NetworkState {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

        networkState = if (isConnected) {
            NetworkState.CONNECTED
        } else {
            NetworkState.DISCONNECTED
        }

        return networkState
    }

    override fun onAvailable(network: Network) {
        networkState = NetworkState.CONNECTED
    }

    override fun onLost(network: Network) {
        networkState = NetworkState.DISCONNECTED
    }
}