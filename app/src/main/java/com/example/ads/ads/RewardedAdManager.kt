package com.example.ads.ads

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ads.ads.helpers.RewardedInterstitialCallback
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


object RewardedAdManager {

    private var isLoadingAds = false
    private var isUserRewarded=false
    private var adCallback: RewardedInterstitialCallback?=null
    var rewardedAd:RewardedAd?=null
    fun loadRewardedAd(context:Context?) {
        context?.let {
            if (rewardedAd == null && !isLoadingAds && !(Constants.isPurchased())) {
                Log.e("RewardedVideo", "LoadingRewardedAd")
                isLoadingAds = true
                isUserRewarded =false
                // Creating  an Ad Request
                val adRequest = AdRequest.Builder().build()
                //Initializing the RewardedAd  objects
                // load Rewarded Ad with the Request
                MobileAds.initialize(context){
                    RewardedAd.load(context, Constants.REWARDED_VIDEO_ADMOB_ID,
                        adRequest, object : RewardedAdLoadCallback() {
                            // creating  RewardedAdLoadCallback for Rewarded Ad with some 2 Override methods
                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                // Handle the error.
                                Log.e("RewardedVideo", loadAdError.message)
                                rewardedAd = null
                                isLoadingAds = false
                            }

                            override fun onAdLoaded(mRewardedAd: RewardedAd) {
                                rewardedAd = mRewardedAd
                                isLoadingAds = false
                                Log.e("RewardedVideo", "Ad was loaded.")

                            }
                        })
                }

            }
        }

    }

    fun setAdCallback(adCallback: RewardedInterstitialCallback?)
    {
        RewardedAdManager.adCallback =adCallback
    }

    fun showRewardedAd(activity:AppCompatActivity,adType:String?, dialogType:String?= Constants.KEY_REWARD_DEFAULT) {
        if (rewardedAd != null) {

            //showing the ad Rewarded Ad if it is loaded
            rewardedAd?.show(activity) { rewardItem: RewardItem ->
                // Handle the reward.
                Log.d("RewardedVideo", "The user earned the reward.")
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                isUserRewarded =true
            }
            rewardedAd?.fullScreenContentCallback=object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()

                    rewardedAd = null
                    // Preload the next rewarded interstitial ad.
                    if(isUserRewarded)
                    {
                        Log.e("RewardedVideo", "Ad was dismissed : User Rewarded.")
                        adCallback?.userRewarded(adType,dialogType)
                    }else
                    {
                        isUserRewarded =false
                        Log.e("RewardedVideo", "Ad was dismissed : Not Rewarded.")
                        adCallback?.onAdClosed(dialogType)
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    Log.e("RewardedVideo", "Ad failed to show : ${adError.message}")

                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                   isUserRewarded =false
                   rewardedAd = null
                }
            }
        } else {
            Log.d("RewardedVideo", "The rewarded ad wasn't ready yet.")
            //Load the Rewarded ad if it is not loaded
            adCallback?.onAdClosed(dialogType)
            loadRewardedAd(activity)


        }
    }


}