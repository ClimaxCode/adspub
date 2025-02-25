package com.climax.ads.adsclas.applovin

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder
import com.climax.ads.R
import com.climax.ads.adsclas.Constants


class ApplovinNative {
    companion object {
        var sploadedNativeAd: MaxAd? = null
        var spnativeAdLoader: MaxNativeAdLoader? = null
    }
    fun loadApplovinNativeAds(
        activity: Activity,
        nativeAdLayout: Int,
        frameLayout2: FrameLayout,
        shimmerFrameLayout: FrameLayout) {
        frameLayout2.visibility = View.GONE
        shimmerFrameLayout.visibility = View.VISIBLE

        if (Constants.applovinNativeId != "" && Constants.applovinNativeId!= "0") {

            Log.d(
                "showApplovinNative",
                "loadApplovinNativeAds12: ${Constants.applovinNativeId}"
            )
            spnativeAdLoader =
                MaxNativeAdLoader(Constants.applovinNativeId, activity)
            spnativeAdLoader?.setRevenueListener { ad: MaxAd? -> }
            spnativeAdLoader?.setNativeAdListener(
                object : MaxNativeAdListener() {
                    override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, nativeAd: MaxAd) {
                        super.onNativeAdLoaded(nativeAdView, nativeAd)
                        Log.e("showApplovinNative", "Native onNativeAdLoaded ")
                        if (activity.isDestroyed) {
                            if (sploadedNativeAd != null) {
                                spnativeAdLoader?.destroy(
                                    sploadedNativeAd
                                )
                            }
                        }

                        // Save ad for cleanup.
                        sploadedNativeAd = nativeAd
                        Log.d(
                            "showApplovinNative",
                            "Native Ad Loaded, Setting visibility to VISIBLE"
                        )
                        frameLayout2.removeAllViews()
                        frameLayout2.addView(nativeAdView)
                        frameLayout2.visibility = View.VISIBLE
                        shimmerFrameLayout.visibility = View.GONE
                    }

                    override fun onNativeAdLoadFailed(s: String, maxError: MaxError) {
                        super.onNativeAdLoadFailed(s, maxError)
                        frameLayout2.visibility = View.GONE
                        shimmerFrameLayout.visibility = View.GONE
                        Log.e("showApplovinNative", "Native onAdFailedToLoad " + maxError.message)
                    }

                    override fun onNativeAdClicked(maxAd: MaxAd) {
                        super.onNativeAdClicked(maxAd)
                        Constants.isOnClickAnyAd = true
                    }
                })
            spnativeAdLoader?.loadAd(
                createNativeAdView(activity, nativeAdLayout)
            )
        }
    }


    private fun createNativeAdView(activity: Activity, nativeAdLayout: Int): MaxNativeAdView? {
        val binder = MaxNativeAdViewBinder.Builder(nativeAdLayout)
            .setTitleTextViewId(R.id.title_text_view)
            .setBodyTextViewId(R.id.body_text_view)
            .setAdvertiserTextViewId(R.id.advertiser_textView)
            .setIconImageViewId(R.id.icon_image_view)
            .setMediaContentViewGroupId(R.id.media_view_container)
            .setOptionsContentViewGroupId(R.id.ad_options_view)
            .setCallToActionButtonId(R.id.cta_button)
            .build()
        return MaxNativeAdView(binder, activity)
    }


}
