package com.climax.ads.adsclas

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.climax.ads.adsclas.CollapsibleBannerAdManager.Companion.BANNER_MANAGER_TAG
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class CollapsibleBannerAd() {
    private var adView: AdView? = null
    companion object {
        const val COLLAPSIBLE_BANNER_TAG = "collapsibleBanner"
    }

    fun removeBannerAdView(adContainerView: FrameLayout?) {
        try {
            adView?.let {
                if (it.parent != null) {
                    (it.parent as ViewGroup).removeView(it) // <- fix
                    //adView = null
                }
                adContainerView?.removeAllViews()


            }
        } catch (e: Exception) {
            Log.e(BANNER_MANAGER_TAG, "ExceptionInAdUnBinding")
        }
    }

    fun loadBanner(context: Context,id :String,adContainerView: FrameLayout,shimmerFrameLayout: ShimmerFrameLayout) {

        if(!Constants.isPurchased() && context.isNetworkAvailable()) {
            adView = AdView(context)

            val adLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

            adView?.layoutParams = adLayoutParams

            adContainerView.addView(adView)

            adView?.adUnitId = id


            val display = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            display.defaultDisplay.getMetrics(outMetrics)


            val density = outMetrics.density

            var adWidthPixels = adContainerView.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

            val extras = Bundle()
            extras.putString("collapsible", "bottom")
            adView?.setAdSize(adSize)
            val adRequest = AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
                .build()
            adView?.loadAd(adRequest)
            adView?.adListener = object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.e(COLLAPSIBLE_BANNER_TAG, "onAdFailedToLoad:${p0.message}")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.e(COLLAPSIBLE_BANNER_TAG, "AdImpression")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.e(COLLAPSIBLE_BANNER_TAG, "onAdLoaded")
                    shimmerFrameLayout.hide()

                }

                override fun onAdOpened() {
                    super.onAdOpened()
                }
            }
        }else{
            adContainerView?.hide()
            shimmerFrameLayout?.hide()
        }
    }




}