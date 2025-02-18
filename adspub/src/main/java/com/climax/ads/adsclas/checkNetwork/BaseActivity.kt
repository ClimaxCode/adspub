package com.climax.ads.adsclas.checkNetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.widget.Toast
import com.climax.ads.adsclas.showNetworkCheckDialog

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LibraryInit.getNetworkLiveData().observe(this, Observer { isConnected ->
            if (isConnected) {
                onNetworkConnected()
            } else {
                onNetworkDisconnected()
            }
        })
    }

    open fun onNetworkConnected() {
       // Toast.makeText(this, "Connected to Internet ✅", Toast.LENGTH_SHORT).show()
    }

    open fun onNetworkDisconnected() {
        showNetworkCheckDialog()
        //Toast.makeText(this, "No Internet ❌", Toast.LENGTH_SHORT).show()
    }
}
