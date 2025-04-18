package com.climax.ads.adsclas

import androidx.lifecycle.MutableLiveData
import com.climax.ads.R
import com.climax.ads.adsclas.applovin.AppLovinBannerAd
import com.climax.ads.adsclas.applovin.AppLovinInterstitial
import com.climax.ads.adsclas.applovin.AppOpenAppLovin
import com.climax.ads.adsclas.applovin.ApplovinNative


object Constants {

    var isSplashAppOpenFail = false
    var isBannerLoaded = false
    var isOpenLocationDialog = false
    var isOnClickAnyAd = false
    var isNewAddLoaded = false
    var isNativeAdfailed = false
    var canShowLoadingAd = false
    var adsInDarkMode = false


    var isPurchasedLiveData = MutableLiveData(false)
    fun isPurchased() = isPurchasedLiveData.value ?: false
    var isFirst = true

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


    /* network dialog */
    var headerText = "No internet connection"
    var descriptionText = "No Internet. Connect And try Again!"
    var bgColorNetworkDialog = R.color.white
    var headerTextColor = R.color.black
    var descriptionTextColor = R.color.black
    var settingTextColor = R.color.white
    var settingButtonColor = R.color.main_color
    var networkImage = R.drawable.ic_no_internet_vector
    var closeIcon = R.drawable.cross_translation
    var closeIconPadding = 9f


    /* language screen text color */
    var countryNameTextColor = R.color.white
    var viewLineColor = R.color.dark_grey
    var languageNameTextColor = R.color.dark_grey
    var languageCardSelectedItemColor = R.color.color_card_item
    var languageCardUnSelectedItemColor = R.color.color_card_item
    var languageLayoutItemColor = R.drawable.selection_bar_item
    var selectedTickIcon = R.drawable.ic_app_language_selected


    /*app lovin ids*/
    var applovinIntersId = "0"
    var applovinNativeId = "0"
    var applovinBannerId = "0"
    var applovinAppOpenId = "0"
    var applovinRewardedId = "0"

    var interstitialAppLovinNew = AppLovinInterstitial()
    var applovinBannerAd = AppLovinBannerAd()
    var appLovinNativeAd = ApplovinNative()

}