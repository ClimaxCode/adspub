package com.climax.code

import android.app.Application
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.climax.ads.adsclas.AppObserver
import com.climax.ads.adsclas.AppOpen
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.Constants.bgColorNetworkDialog
import com.climax.ads.adsclas.checkNetwork.LibraryInit
import com.climax.code.onBoarding.OnboardingItem
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList
import com.climax.code.utils.ConstantsCustomizations.setonBoarding_Bg_Color
import com.google.android.ump.ConsentDebugSettings


@Suppress("DEPRECATION")
class App : Application() {
    private var appObserver: AppObserver? = null
    override fun onCreate() {
        super.onCreate()
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        LibraryInit.init(this)
        bgColorNetworkDialog = R.color.card_color

        try {
//            AppLovinSdk.getInstance.i(this) { configuration ->
//                Log.d("AppLovin", "Initialized with country code: ${configuration.countryCode}")
//            }


            // Create the initialization configuration
            val initConfig = AppLovinSdkInitializationConfiguration.builder("«SDK-key»")
                .setMediationProvider(AppLovinMediationProvider.MAX)
                .build()

            // Initialize the SDK with the configuration
            AppLovinSdk.getInstance(this).initialize(initConfig) { sdkConfig ->
                Log.d("AppLovin", "Initialized with country code: ${sdkConfig.countryCode}")
            }
            ConsentDebugSettings.Builder(this).addTestDeviceHashedId("2D34734E32B43287642FE9D9F7A04BEF")
        } catch (e: Exception) {
            Log.e("AdsInit", "Error initializing SDKs", e)
        }
        appObserver = AppObserver(
            appOpen = AppOpen(),
            "ca-app-pub-3940256099942544/9257395921",
            "MainActivity"
        )
        appObserver?.let {
            ProcessLifecycleOwner.get().lifecycle.addObserver(it)
            this.registerActivityLifecycleCallbacks(it)
        }

        Constants.onBoardingFullScreenNativeId = "ca-app-pub-3940256099942544/1044960115"
        setonBoarding_Bg_Color = getColor(com.climax.code.R.color.white)
        onBoardingItemsList.add(
            OnboardingItem(
               "1",
                getString(R.string.txt_desc_onBoard1),
                R.drawable.img1
            )
        )
        onBoardingItemsList.add(
            OnboardingItem(
               "2",
                getString(R.string.txt_desc_onBoard2),
                R.drawable.img1
            )
        )
        onBoardingItemsList.add(
            OnboardingItem(
              "3",
                getString(R.string.txt_desc_onBoard3),
                R.drawable.img1
            )
        )

        onBoardingItemsList.add(
            OnboardingItem(
              "4",
                getString(R.string.txt_desc_onBoard3),
                R.drawable.img1
            )
        )
//        onBoardingItemsList.add(
//            OnboardingItem(
//                "5",
//                getString(R.string.txt_desc_onBoard3),
//                R.drawable.img1
//            )
//        )
    }
}