package com.climax.code

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.climax.ads.adsclas.callNativeAd
import com.climax.code.databinding.ActivityNativeAdsCallBinding

class NativeAdsCallActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNativeAdsCallBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.getStringExtra("style").equals("one")) {
            binding.one.visibility = View.VISIBLE

            //       callNativeAd("ca-app-pub-3940256099942544/1044960115","large",false,true,{},{},{})
        } else if (intent.getStringExtra("style").equals("two")) {
            binding.two.visibility = View.VISIBLE
            callNativeAd("ca-app-pub-3940256099942544/1044960115", "native1", false, true,
                {
                    Log.d("adss", "onCreate: loaded")
                }, {
                    Log.d("adss", "onCreate: failes")
                }, {

                })


        } else if (intent.getStringExtra("style").equals("three")) {
            binding.three.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native2",
                false,
                true,
                {},
                {},
                {})
        } else if (intent.getStringExtra("style").equals("four")) {
            binding.four.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native3",
                false,
                true,
                {},
                {},
                {})
        } else if (intent.getStringExtra("style").equals("five")) {
            binding.five.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native4",
                false,
                true,
                {},
                {},
                {})
        } else if (intent.getStringExtra("style").equals("six")) {
            binding.six.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native5",
                false,
                true,
                {},
                {},
                {})
        } else if (intent.getStringExtra("style").equals("seven")) {
            binding.seven.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native6",
                false,
                true,
                {},
                {},
                {})
        } else if (intent.getStringExtra("style").equals("eight")) {
            binding.eight.visibility = View.VISIBLE
            callNativeAd("ca-app-pub-3940256099942544/1044960115", "small", false, true, {}, {}, {})
        }


    }
}