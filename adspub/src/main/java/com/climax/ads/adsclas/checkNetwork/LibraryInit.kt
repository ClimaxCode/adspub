package com.climax.ads.adsclas.checkNetwork


import android.content.Context

object LibraryInit {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getNetworkLiveData(): NetworkLiveData {
        return NetworkLiveData.getInstance(appContext)
    }
}
