package com.climax.ads.adsclas.helpers

import com.climax.ads.adsclas.Constants


interface RewardedInterstitialCallback {
    fun onAdClosed(dialogType:String?= Constants.KEY_REWARD_DEFAULT)
    fun userRewarded(adType:String?,dialogType:String?= Constants.KEY_REWARD_DEFAULT)
    fun userCancelToWatchAd(dialogType:String?= Constants.KEY_REWARD_DEFAULT)
    fun userGoPremium(dialogType:String?= Constants.KEY_REWARD_DEFAULT)
    fun userShowAd(adType:String?,dialogType:String?= Constants.KEY_REWARD_DEFAULT)
}