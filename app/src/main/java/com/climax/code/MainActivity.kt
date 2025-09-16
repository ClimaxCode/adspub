package com.climax.code


import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.climax.ads.adsclas.checkAndShowInterstitial
import com.climax.ads.adsclas.checkNetwork.BaseActivity
import com.climax.ads.adsclas.exit1
import com.climax.ads.adsclas.exit3
import com.climax.ads.adsclas.exit4
import com.climax.ads.adsclas.loadPreInterstitial
import com.climax.ads.adsclas.preLoadLargeNativeAd
import com.climax.code.applanguages.AppLanguagesModel
import com.climax.code.applanguages.ExtensionFun.languagesList
import com.climax.code.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var languageLIst : ArrayList<AppLanguagesModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        clicks()

        languageLIst = languagesList
        Log.d("langlist", "onCreate: ${languageLIst?.size} -- ${languageLIst.toString()}")
    }

    fun clicks() {
        binding.showLanguageListBtn.setOnClickListener {
            startActivity(Intent(this,AppLanguagesListView::class.java))
        }
        binding.buttonexit1.setOnClickListener {
//            preLoadRewardedVideos(
//                this@MainActivity,
//                "ca-app-pub-3940256099942544/5224354917",
//                showLoadingDialog = true
//            ) {
//                if (it) {
//                    showRewarded(
//                        false,
//                        waitingTime = 8000L,
//                        "ca-app-pub-3940256099942544/5224354917",
//                        false,
//                        false,
//                        onShowAdCompletedAction = {
                            this.exit1(
                                "ca-app-pub-3940256099942544/2247696110","native2",
                                true,
                                R.color.text_color_green,
                                R.color.txt_color,
                                R.color.sub_color, true, R.color.bg_color_

                            ) {
                            }
////                            binding.proViewBtn.visibility = View.GONE
////                            binding.watchAdViewBtn.visibility = View.GONE
////                            appliedSelectedFont()
////                            isShowInterstitialsAd = true
//                        },
//                        onFailedAdAction = {
//                            Toast.makeText(
//                                this@MainActivity,
//                                "Currently Reward is not Available. Try again",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        })
//                } else {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Currently Reward is not Available. Try again",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }

        }
        binding.buttonexit2.setOnClickListener {
            this.exit4(
                "ca-app-pub-3940256099942544/2247696110","native10",
                true,
                R.color.main_color,
                R.color.white,
                R.color.sub_color,false,R.color.bg_color_
            ) {

            }
        }

        binding.buttonexit3.setOnClickListener {
            this.exit3(
                "/21775744923/example/native","native9",
                true,
                R.color.text_color_green,
                R.color.txt_color,
                R.color.sub_color,false, R.color.white
            ) {

            }
        }
        binding.fullNative.setOnClickListener {
//            val rateAppDialog = RateUsDialog2.newInstance(
//             title = "Exit", exitTitle = "Later",
//                onActionExit = {
//                    // Action to perform when the dialog triggers this function
//                    // Log.d("RateAppDialog", "User completed action!")
//                    Toast.makeText(this, "Action onActionExit!", Toast.LENGTH_SHORT).show()
//                }, onActionFeedback = {
//                    Toast.makeText(this, "Action onActionFeedback!", Toast.LENGTH_SHORT).show()
//
//                },
//                onActionRateus = {
//                    // Action to perform when the dialog triggers this function
//                    // Log.d("RateAppDialog", "User completed action!")
//                    Toast.makeText(this, "Action onActionRateus!", Toast.LENGTH_SHORT).show()
//                }
//            )
//
//            rateAppDialog.show(supportFragmentManager, "rateAppDialog")
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "one")
            startActivity(intent)

        }
        binding.native1.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "two")
            startActivity(intent)

        }

        binding.native9.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "nine")
            startActivity(intent)

        }

        binding.native10.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "ten")
            startActivity(intent)

        }
        binding.native2.setOnClickListener {
            var intent = Intent(this, NativeAdsCallActivity::class.java)
            intent.putExtra("style", "three")
            startActivity(intent)


        }
        preLoadLargeNativeAd("ca-app-pub-3940256099942544/2247696110")

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

        this.loadPreInterstitial("ca-app-pub-3940256099942544/103317371212")

        binding.inter1.setOnClickListener {
            this.checkAndShowInterstitial(
                true,
                "ca-app-pub-3940256099942544/103317371212",
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