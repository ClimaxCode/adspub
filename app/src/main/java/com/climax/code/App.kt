package com.climax.code

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import com.climax.ads.adsclas.AppObserver
import com.climax.ads.adsclas.AppOpen
import com.climax.ads.adsclas.Constants
import com.climax.code.onBoarding.OnboardingItem
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList
import com.climax.code.utils.ConstantsCustomizations.setonBoarding_Bg_Color

class App : Application() {
    private var appObserver: AppObserver? = null
    override fun onCreate() {
        super.onCreate()
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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