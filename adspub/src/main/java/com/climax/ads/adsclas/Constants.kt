package com.climax.ads.adsclas

import android.widget.Button
import androidx.lifecycle.MutableLiveData
import com.climax.ads.R


object Constants {

    const val FB_TAG = "fb_inter"
    var adsButtonColor= R.color.Adscolor
    var isSplashAppOpenFail = false
    var isBannerLoaded = false
    var isOpenLocationDialog = false
    const val KEY_PANGLE_APP_ID = "8495093"
    var FBTAG: String = "facebook_native"
    var isOnClickAnyAd= false
    var isNewAddLoaded= false
    var isNativeAdfailed= false

    var inlineBannerId = ""
    var fbNativeAdId = ""
    var fbInterstitialsAdId = ""
    var isLoadedFbInterstitialsAd = false

    var SPLASH_DELAY_TIME = 10000L

    var isPurchasedLiveData = MutableLiveData(false)
    fun isPurchased() = isPurchasedLiveData.value ?: false
    var isFirst=true

    var interstitial = Interstitial()
    var appOpen = AppOpen()
    var native = Native()
    var smallNative = NativeSmall()
    var largeNative = NativeLarge()

    var collapsiblebannerAd = CollapsibleBannerAd()


    var isFailGeneratedBtn = false
    var isLanguageClick = false
    var isGeneratedScreenItem = false
    var showAllFragments = true
    var isShowAdapterOrCollapsibleMainScreen = true
    var showFullNative = NativeFullScreen()

    var isLoadNativeAd = false

    var isAppLanguagesScreenAdChange = false
    var isShowAdStatusAreaCalculator = false
    var isShowAdStatusDistanceCalculator = false
    var isShowAdStatusLocationScreen = false
    var isInterstitialShowed = false
    var isAppOpenShowed = false
    var clickCount = 0
    var clickCountPangle = 0
    var interstitialAdCount = 0
    var intersAdConvertScreenCount = 0
    var OTHER_AD_DISPLAYED = false
    var isLastAdWasAdmob = true
    var ADS_INITIALIZATION_COMPLETED = false
    var appIsForeground = true
    var isFailInterstitialAd = false
    var isLoadedAdInters = false
    var isLoadedAdIntersPangle = false
    var scannerWaitingTimer: Long = 4000
    var canShowRateUsDialog = false
    var isControlOnboardingScreen = 1
    var isControlFullScreeNative = 0

    var splashAppOpenIdff = ""

    var isShowSplashNativeOrBanner = true
    var onResumeAppOpenId = ""
    var isIntersLanguageOnOff = true
    var languageInterstitialsId = ""
    var languageNativeAdId = ""
    var languageCollapsibleId = ""
    var MainScreenCollapsibleId = ""
    var rateUsBannerAdId = ""
    var mainNativeAdId = ""
    var AreaScreenNativeId = ""
    var areaScreenInterstitialId = ""
    var distanceScreenNativeId = ""
    var distanceScreenInterstitialsId = ""
    var locationScreenNativeId = ""
    var locationScreenInterstitialsId = ""
    var AreaScreenScreenCollapsibleId = ""
    var distanceScreenScreenCollapsibleId = ""
    var locationScreenCollapsibleId = ""
    var convertorScreenCollapsibleId = ""
    var convertorScreenInterstitialsId = ""
    var convertorScreenNativeId = ""
    var compassScreenCollapsibleId = ""
    var compassScreenInterstitialsId = ""
    var speedMeterScreenCollapsibleId = ""
    var speedMeterScreenInterstitialsId = ""
    var savedLocationScreenCollapsibleId = ""
    var savedLocationScreenIntersId = ""
    var appThemesScreenCollapsibleId = ""
    var appThemesScreenIntersId = ""

    var splashScreenInterstitialId = ""
    var homeScreenInterstitialId = ""
    var languageScreenInterstitialId = ""
    var createOwnButtonInterstitialId = ""
    var splashNativeAd = ""
    var languageNativeAd = ""
    var createThemeNativeAd = ""
    var enableKeyboardNativeAd = ""
    var showHomeThemeCounter = 0
    var isShowCreateOwnInters = false
    var onboardingNativeAdId = ""


    var splashBannerAdId = ""
    var tempCollapsibleBannerAdId = ""
    var isShowFullScreenNativeAd = true
    var fullScreenNativeId = ""
    var onBoardingFullScreenNativeId = ""
    var fullNativeButtonColor = R.color.Adscolor
    var fullNativeButtonTextColor = R.color.white
    var fullNativeBGColor = R.color.white

    var exitAppNativeAdId = ""
    var settingNativeAdId = ""

    var mainScreenAdoptiveBannerId = ""

    var scanIntersFreq = 2
    var scanIntersAdId = ""
    var generateScreenBannerId = ""
    var generateScreenNativeId = ""
    var barCodeNativeAdId = ""
    var backInterstitialsAdId = ""

    var historyBottomBannerId = ""
    var historyInterstitialsId = ""

    var settingBannerAdId = ""
    var isOnOrOffSettingAd = false

    var resultScreenCollapsibleId = ""


    const val KEY_REWARD_DEFAULT = "default_reward"
    var REWARDED_VIDEO_ADMOB_ID = ""
    var rewarded = Rewarded()


    /* pangle interstitial ad id */

    var pangleInterstitialId = ""
    var pangleNativeId = ""

}