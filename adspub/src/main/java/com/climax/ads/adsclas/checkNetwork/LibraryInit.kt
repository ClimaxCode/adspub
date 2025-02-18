package com.climax.ads.adsclas.checkNetwork

import android.content.Context

object LibraryInit {

    @Volatile
    private var appContext: Context? = null

    fun init(context: Context) {
        if (appContext == null) {
            synchronized(this) {
                if (appContext == null) {
                    appContext = context.applicationContext
                }
            }
        }
    }

    fun getNetworkLiveData(): NetworkLiveData {
        return appContext?.let {
            NetworkLiveData.getInstance(it)
        } ?: throw IllegalStateException("LibraryInit.init(context) must be called before using getNetworkLiveData()")
    }
}
