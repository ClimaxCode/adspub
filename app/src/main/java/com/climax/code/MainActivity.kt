package com.climax.code

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.climax.ads.adsclas.checkAndShowInterstitial
import com.climax.ads.adsclas.exit1
import com.climax.ads.adsclas.exit2
import com.climax.ads.adsclas.loadPreInterstitial
import com.climax.code.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        clicks()

    }

    fun clicks() {
        binding.buttonexit1.setOnClickListener {
            this.exit1("ca-app-pub-3940256099942544/2247696110") {
            }
        }
        binding.buttonexit2.setOnClickListener {
            this.exit2("ca-app-pub-3940256099942544/2247696110") {

            }
        }
        binding.fullNative.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "one")
            startActivity(intent)

        }
        binding.native1.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "two")
            startActivity(intent)

        }
        binding.native2.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "three")
            startActivity(intent)

        }
        binding.native3.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "four")
            startActivity(intent)

        }
        binding.native4.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "five")
            startActivity(intent)

        }
        binding.native5.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "six")
            startActivity(intent)

        }
        binding.native6.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "seven")
            startActivity(intent)
        }
        binding.small.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "eight")
            startActivity(intent)
        }

        binding.collapsible.setOnClickListener {
            var intent = Intent(this, BannerActivity::class.java)
            intent.putExtra("style", "collap")
            startActivity(intent)
        }

        binding.inline.setOnClickListener {
            var intent = Intent(this, BannerActivity::class.java)
            intent.putExtra("style", "inline")
            startActivity(intent)
        }

        binding.adaptive.setOnClickListener {
            var intent = Intent(this, BannerActivity::class.java)
            intent.putExtra("style", "adaptive")
            startActivity(intent)
        }

        this.loadPreInterstitial("ca-app-pub-3940256099942544/1033173712")

        binding.inter1.setOnClickListener {
            this.checkAndShowInterstitial(
                true,
                "ca-app-pub-3940256099942544/1033173712",
                true,
                0,
                onShowAdCompletedAction = {
                    var intent = Intent(this@MainActivity, InterstitalActivity::class.java)
                    intent.putExtra("failed", false)
                    startActivity(intent)
                },
                onInterstitialFailed = {
                    var intent = Intent(this@MainActivity, InterstitalActivity::class.java)
                    intent.putExtra("failed", true)
                    startActivity(intent)
                })
        }

        binding.inter2.setOnClickListener {

            this.checkAndShowInterstitial(
                true,
                "ca-app-pub-3940256099942544/1033173712",
                true,
                5000,
                onShowAdCompletedAction = {

                    var intent = Intent(this@MainActivity, InterstitalActivity::class.java)
                    intent.putExtra("failed", false)
                    startActivity(intent)
                },
                onInterstitialFailed = {
                    var intent = Intent(this@MainActivity, InterstitalActivity::class.java)
                    intent.putExtra("failed", true)
                    startActivity(intent)
                })
        }


    }
}