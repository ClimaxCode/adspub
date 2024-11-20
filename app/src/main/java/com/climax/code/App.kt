package com.climax.code

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import com.climax.ads.adsclas.AppObserver
import com.climax.ads.adsclas.Constants

class App:Application() {
    private var appObserver: AppObserver? = null
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        appObserver = AppObserver(appOpen = Constants.appOpen,"ca-app-pub-3940256099942544/9257395921","MainActivity")
        appObserver?.let {
            ProcessLifecycleOwner.get().lifecycle.addObserver(it)
            this.registerActivityLifecycleCallbacks(it)
        }
    }
}