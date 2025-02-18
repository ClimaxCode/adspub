package com.climax.ads.adsclas.checkNetwork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData


class NetworkLiveData private constructor(private val context: Context) : LiveData<Boolean>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            postValue(isConnected())
        }
    }

    init {
        postValue(isConnected()) // Initial network state
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }

    private fun isConnected(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    companion object {
        @Volatile private var instance: NetworkLiveData? = null

        fun getInstance(context: Context): NetworkLiveData {
            return instance ?: synchronized(this) {
                instance ?: NetworkLiveData(context.applicationContext).also { instance = it }
            }
        }
    }
}
