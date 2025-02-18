package com.climax.ads.adsclas.checkNetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.climax.ads.adsclas.showNetworkCheckDialog

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure LibraryInit is initialized
        try {
            LibraryInit.getNetworkLiveData().observe(this, Observer { isConnected ->
                if (isConnected) {
                    onNetworkConnected()
                } else {
                    onNetworkDisconnected()
                }
            })
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    open fun onNetworkConnected() {
    }

    open fun onNetworkDisconnected() {
        if (!isFinishing) {
            showNetworkCheckDialog() // Ensure activity is not finishing before showing a dialog
        }
    }
}
