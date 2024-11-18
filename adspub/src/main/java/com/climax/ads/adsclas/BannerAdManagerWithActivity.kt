package com.climax.ads.ads

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.ViewGroup
import android.widget.FrameLayout
import com.climax.ads.ads.hide
import com.climax.ads.ads.Constants.isBannerLoaded
import com.facebook.shimmer.ShimmerFrameLayout


import com.google.android.gms.ads.*

class BannerAdManagerWithActivity {
    companion object {
        const val BANNER_MANAGER_TAG = "BannerActivity"
    }

    var adView: AdView? = null

    /* load banner ad on every session*/
    fun loadBannerAd(context: Activity?, id : String, adContainerView: FrameLayout?,shimmerFrameLayout: ShimmerFrameLayout) {
        // Create an ad request.
        if(!Constants.isPurchased()){
            context?.let {
                Log.e(BANNER_MANAGER_TAG, "LoadNewBannerAd${Constants.splashBannerAdId}")

                val adRequest = AdRequest.Builder().build()
                adView = AdView(context)
                adView?.adUnitId = id
                adContainerView?.removeAllViews()
                adContainerView?.addView(adView)
                val adSize = getAdSize(context)
                adView?.setAdSize(adSize)

                adView?.loadAd(adRequest)

                adView?.adListener = object : AdListener() {
                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                        Log.e(BANNER_MANAGER_TAG, "onAdFailedToLoad:${p0.message}")
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        Log.e(BANNER_MANAGER_TAG, "AdImpression")
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        isBannerLoaded = true
                        Log.e(BANNER_MANAGER_TAG, "onAdLoaded")
                        if(!Constants.isPurchased()){
                            adContainerView?.show()
                            shimmerFrameLayout?.hide()
                        }else{
                            adContainerView?.hide()
                            shimmerFrameLayout?.hide()
                        }

                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
                    }
                }

            }
        }else{
            adContainerView?.hide()
            shimmerFrameLayout?.hide()
        }


    }


    fun removeBannerAdView(adContainerView: FrameLayout?) {
        try {
            adView?.let {
                if (it.parent != null) {
                    (it.parent as ViewGroup).removeView(it) // <- fix
                }
                adContainerView?.removeAllViews()


            }
        } catch (e: Exception) {
            Log.e(BANNER_MANAGER_TAG, "ExceptionInAdUnBinding")
        }


    }

    private fun getAdSize(context: Activity): AdSize {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display: Display? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display
        } else {
            context.windowManager.defaultDisplay
        }
        val outMetrics = DisplayMetrics()
        display?.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }

    fun onPauseBannerAd() {
        Log.e(BANNER_MANAGER_TAG, "OnPauseBannerAd")
        adView?.pause()
    }

    fun onResumeBannerAd() {
        Log.e(BANNER_MANAGER_TAG, "onResumeBannerAd")
        adView?.resume()
    }

    /* destroy banner Ad on Application close*/
    fun destroyBannerAd() {
        adView?.destroy()
    }
}