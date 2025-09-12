package com.climax.code

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.climax.code.R
import com.climax.ads.adsclas.Constants.isNativeAdfailed
import com.climax.ads.adsclas.callNativeAd
import com.climax.ads.adsclas.showLargeNative
import com.climax.code.databinding.ActivityNativeAdsCallBinding

class NativeAdsCallActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNativeAdsCallBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("Logg", "onCreate: $isNativeAdfailed")
        if (intent.getStringExtra("style").equals("one")) {
            binding.one.visibility = View.VISIBLE



            callNativeAd("ca-app-pub-3940256099942544/1044960115", "large", false, true, {
                Log.d("adss", "onCreate: loaded")
            }, {}, {}, R.color.red, R.color.sub_color, R.color.sub_color)
        } else if (intent.getStringExtra("style").equals("two")) {
            binding.two.visibility = View.VISIBLE
            callNativeAd("ca-app-pub-3940256099942544/1044960115", "native1", false, true,
                {
                    Log.d("adss", "onCreate: loaded")
                }, {
                    Log.d("adss", "onCreate: failes")
                }, {

                }, R.color.button_active, R.color.sub_color, R.color.sub_color
            )

//            showLargeNative(
//                "ca-app-pub-3940256099942544/1044960115", "native1",
//                com.climax.ads.R.layout.native1,
//                this.findViewById(com.climax.ads.R.id.ad_root),
//                binding.layout.shimmer1,
//                shimmer,
//                preLoad,
//                loadNewAd,
//                actionLoaded,
//                actionFailed,
//                tryToShowAgain,
//                actionButtonColor,
//                actionButtonTextColor,
//                bgColor
//            )

        } else if (intent.getStringExtra("style").equals("three")) {
            binding.three.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native2",
                false,
                true,
                { Log.d("adss", "onCreate: loaded") },
                { Log.d("adss", "onCreate: failes") },
                {}, R.color.red, R.color.black, R.color.sub_color
            )
        } else if (intent.getStringExtra("style").equals("four")) {
            binding.four.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "small_native",
                false,
                true,
                { Log.d("adss", "onCreate: loaded") },
                { Log.d("adss", "onCreate: failes") },
                {}, R.color.black, R.color.white, R.color.dialogtxt
            )
        } else if (intent.getStringExtra("style").equals("five")) {
            binding.five.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native4",
                false,
                true,
                { Log.d("adss", "onCreate: loaded") },
                { Log.d("adss", "onCreate: failes") },
                {}, R.color.red, R.color.black, R.color.sub_color
            )
        } else if (intent.getStringExtra("style").equals("six")) {
            binding.six.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native5",
                false,
                true,
                { Log.d("adss", "onCreate: loaded") },
                {},
                {}, R.color.red, R.color.sub_color, R.color.sub_color
            )
        } else if (intent.getStringExtra("style").equals("seven")) {
            binding.seven.visibility = View.VISIBLE

            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native9",
                false,
                true,
                { Log.d("adss", "onCreate: loaded") },
                {},
                {}, R.color.red, R.color.sub_color, R.color.ads_bg
            )
        } else if (intent.getStringExtra("style").equals("eight")) {
            binding.eight.visibility = View.VISIBLE
            callNativeAd(
                "ca-app-pub-3940256099942544/1044960115",
                "native10",
                false,
                true,
                {},
                {},
                {},
                R.color.red,
                R.color.sub_color,
                R.color.sub_color
            )
        }
//        else if (intent.getStringExtra("style").equals("s")) {
//            binding.eight.visibility = View.VISIBLE
//            callNativeAd(
//                "ca-app-pub-3940256099942544/1044960115",
//                "native10",
//                false,
//                true,
//                {},
//                {},
//                {},
//                R.color.red,
//                R.color.sub_color,
//                R.color.sub_color
//            )
//        }


    }
}