package com.climax.code

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.climax.ads.adsclas.checkAndShowInterstitial
import com.climax.ads.adsclas.exit1
import com.climax.ads.adsclas.exit2
import com.climax.ads.adsclas.loadPreInterstitial
import com.climax.ads.adsclas.preLoadLargeNativeAd
import com.climax.ads.adsclas.preLoadRewardedVideos
import com.climax.ads.adsclas.showRewarded
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
            preLoadRewardedVideos(this@MainActivity,"ca-app-pub-3940256099942544/5224354917", showLoadingDialog = true) {
                if (it) {
                    showRewarded(
                        false,
                        waitingTime = 8000L,
                        "ca-app-pub-3940256099942544/5224354917",
                        false,
                        false,
                        onShowAdCompletedAction = {
                            this.exit1("ca-app-pub-3940256099942544/2247696110",R.color.main_color,R.color.white,R.color.main_color,R.color.sub_color) {
                            }
//                            binding.proViewBtn.visibility = View.GONE
//                            binding.watchAdViewBtn.visibility = View.GONE
//                            appliedSelectedFont()
//                            isShowInterstitialsAd = true
                        },
                        onFailedAdAction = {
                            Toast.makeText(
                                this@MainActivity,
                                "Currently Reward is not Available. Try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Currently Reward is not Available. Try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            this.exit1("ca-app-pub-3940256099942544/2247696110",R.color.main_color,R.color.white,R.color.main_color,R.color.sub_color) {
            }
        }
        binding.buttonexit2.setOnClickListener {
            this.exit2("ca-app-pub-3940256099942544/2247696110",R.color.text_color_green,R.color.txt_color,R.color.sub_color) {

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
        preLoadLargeNativeAd("ca-app-pub-3940256099942544/1044960115")

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
                    var intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
                    intent.putExtra("failed", false)
                    startActivity(intent)
                },
                onInterstitialFailed = {
                    var intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
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

                    var intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
                    intent.putExtra("failed", false)
                    startActivity(intent)
                },
                onInterstitialFailed = {
                    var intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
                    intent.putExtra("failed", true)
                    startActivity(intent)
                })
        }


    }


}