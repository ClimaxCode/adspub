package com.climax.ads.adsclas

import android.widget.Button
import androidx.lifecycle.MutableLiveData
import com.climax.ads.R


object Constants {

    var isSplashAppOpenFail = false
    var isBannerLoaded = false
    var isOpenLocationDialog = false
    var isOnClickAnyAd= false
    var isNewAddLoaded= false
    var isNativeAdfailed= false


    var isPurchasedLiveData = MutableLiveData(false)
    fun isPurchased() = isPurchasedLiveData.value ?: false
    var isFirst=true

    var interstitial = Interstitial()
    var appOpen = AppOpen()
    var native = Native()
    var smallNative = NativeSmall()
    var largeNative = NativeLarge()

    var collapsiblebannerAd = CollapsibleBannerAd()

    var showFullNative = NativeFullScreen()

    var isLoadNativeAd = false

    var isInterstitialShowed = false
    var isAppOpenShowed = false
    var clickCount = 0
    var interstitialAdCount = 0
    var intersAdConvertScreenCount = 0
    var OTHER_AD_DISPLAYED = false
    var isLastAdWasAdmob = true
    var ADS_INITIALIZATION_COMPLETED = false
    var appIsForeground = true
    var isFailInterstitialAd = false
    var isLoadedAdInters = false

    var languageNativeAdId = ""


    var onboardingNativeAdId = ""


    var splashBannerAdId = ""
    var onBoardingFullScreenNativeId = ""
    var fullNativeButtonColor = R.color.Adscolor
    var fullNativeButtonTextColor = R.color.white
    var fullNativeBGColor = R.color.white


    const val KEY_REWARD_DEFAULT = "default_reward"
    var REWARDED_VIDEO_ADMOB_ID = ""
    var rewarded = Rewarded()



    var headerText = "No internet connection"
    var descriptionText = "No Internet. Connect And try Again!"
    var bgColorNetworkDialog = R.color.white
    var headerTextColor = R.color.black
    var descriptionTextColor = R.color.black
    var settingTextColor = R.color.white
    var settingButtonColor = R.color.main_color
    var networkImage = R.drawable.ic_no_internet_vector
    var closeIcon = R.drawable.cross_translation

}