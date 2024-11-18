package com.climax.ads.ads

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.climax.ads.R
import com.climax.ads.ads.Constants.isLoadNativeAd
import com.climax.ads.ads.enums.AdState


import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NativeLarge {

    private var nativeAd: NativeAd? = null
    private var adState = AdState.LOAD
    private var checkTimeOut = true
    var lastNativeAdId:String= Constants.languageNativeAdId
    private val countDownTimer = object : CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            checkTimeOut = false
        }

        override fun onFinish() {
            checkTimeOut = true
        }
    }

    fun showNative(
        activity: Activity,
        nativeAdId: String,
        nativeAdLayout: Int,
        container: ConstraintLayout?,
        frameLayout: FrameLayout,
        shimmerFrameLayout: FrameLayout,
        loadNewAd:Boolean=true,
        actionLoaded: (() -> Unit)? = null,
        actionFailed: (() -> Unit)? = null,
        tryToShowAgain: ((Boolean) -> Unit)? = null
    ) {
        lastNativeAdId=nativeAdId

        when (adState) {
            AdState.LOAD -> loadNative(
                activity,
                lastNativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout
            )
            AdState.LOADED -> {
                if(loadNewAd){
                    loadNative(
                        activity,
                        lastNativeAdId,
                        nativeAdLayout,
                        container,
                        frameLayout,
                        shimmerFrameLayout
                    )
                }else{
                    populateNativeAdView(
                        activity,
                        nativeAdLayout,
                        nativeAd,
                        container,
                        frameLayout,
                        shimmerFrameLayout
                    )
                }

            }
            AdState.LOADING -> {
                tryToShowAgain?.invoke(true)
            }
            AdState.FAILED -> loadNative(
                activity,
                lastNativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
            AdState.SHOWN_FAILED -> {}
            AdState.SHOWING -> {}
            AdState.IMPRESSION -> {
                if(loadNewAd){
                    populateNativeAdView(
                        activity,
                        nativeAdLayout,
                        nativeAd,
                        container,
                        frameLayout,
                        shimmerFrameLayout
                    )
                    loadNative(
                        activity,
                        lastNativeAdId,
                        nativeAdLayout,
                        container,
                        frameLayout,
                        shimmerFrameLayout,
                        actionLoaded,
                        actionFailed
                    )
                }else{
                    if (checkTimeOut) {
                        loadNative(
                            activity,
                            lastNativeAdId,
                            nativeAdLayout,
                            container,
                            frameLayout,
                            shimmerFrameLayout,
                            actionLoaded,
                            actionFailed
                        )
                    } else {
                        populateNativeAdView(
                            activity,
                            nativeAdLayout,
                            nativeAd,
                            container,
                            frameLayout,
                            shimmerFrameLayout
                        )
                    }
                }

            }
            AdState.DISMISSED -> {}
            AdState.AD_CLICKED -> loadNative(
                activity,
                lastNativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
        }
    }

    fun showPreLoadNative(
        activity: Activity,
        nativeAdLayout: Int,
        container: ConstraintLayout,
        frameLayout: FrameLayout,
        shimmerFrameLayout: FrameLayout,
        actionLoaded: (() -> Unit)? = null,
        actionFailed: (() -> Unit)? = null
    ) {

        Log.d("FAHAD", "precheckTimeOut: $checkTimeOut")
        Log.d("FAHAD", "preAdState: $adState")
        when (adState) {
            AdState.LOAD -> loadNative(
                activity,
                lastNativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
            AdState.LOADED -> {
                adState = AdState.SHOWING
                populateNativeAdView(
                    activity,
                    nativeAdLayout,
                    nativeAd,
                    container,
                    frameLayout,
                    shimmerFrameLayout
                )
            }
            AdState.LOADING -> {}
            AdState.FAILED -> loadNative(
                activity,
                lastNativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
            AdState.SHOWN_FAILED -> {}
            AdState.SHOWING -> {
                adState = AdState.SHOWING
                CoroutineScope(Dispatchers.Main).launch {
                    populateNativeAdView(
                        activity,
                        nativeAdLayout,
                        nativeAd,
                        container,
                        frameLayout,
                        shimmerFrameLayout
                    )
                }.invokeOnCompletion {
                    actionLoaded?.invoke()
                }
            }
            AdState.IMPRESSION -> {
                if (checkTimeOut) {
                    loadNative(
                        activity,
                        lastNativeAdId,
                        nativeAdLayout,
                        container,
                        frameLayout,
                        shimmerFrameLayout,
                        actionLoaded,
                        actionFailed
                    )
                } else {
                    adState = AdState.SHOWING
                    CoroutineScope(Dispatchers.Main).launch {
                        populateNativeAdView(
                            activity,
                            nativeAdLayout,
                            nativeAd,
                            container,
                            frameLayout,
                            shimmerFrameLayout
                        )
                    }.invokeOnCompletion {
                        actionLoaded?.invoke()
                    }
                }
            }
            AdState.DISMISSED -> {}
            AdState.AD_CLICKED -> loadNative(
                activity,
                lastNativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
        }
    }

    private fun loadNative(
        activity: Activity,
        nativeAdId:String,
        nativeAdLayout: Int,
        container: ConstraintLayout?,
        frameLayout: FrameLayout,
        shimmerFrameLayout: FrameLayout,
        actionLoaded: (() -> Unit)? = null,
        actionFailed: (() -> Unit)? = null
    ) {
        val videoOptions = VideoOptions.Builder().build()
        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()

        val adLoader = AdLoader.Builder(
            activity,
            nativeAdId
        )
            .forNativeAd { ad: NativeAd ->
                adState = AdState.LOADED
                //nativeAd?.destroy()
                nativeAd = ad
                adState = AdState.SHOWING
                countDownTimer.start()
                Log.d("native", "loadNative: ${nativeAd.hashCode()}")
                Log.d("native", "checkTimeOutLoad: $checkTimeOut")

                populateNativeAdView(
                    activity,
                    nativeAdLayout,
                    ad,
                    container,
                    frameLayout,
                    shimmerFrameLayout
                )
                actionLoaded?.invoke()
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adState = AdState.FAILED
                    actionFailed?.invoke()
                    container?.hide()
                    frameLayout.hide()
                    shimmerFrameLayout.hide()
                    Log.d("nativeAd", "onAdFailedToLoad: ")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adState = AdState.LOADED
                    isLoadNativeAd = true
                    Log.d("nativeAd", "onAdLoaded: ")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    adState = AdState.IMPRESSION
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    adState = AdState.AD_CLICKED
                }
            })
            .withNativeAdOptions(
                adOptions
            )
            .build()

        adState = AdState.LOADING
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun preLoadNative(
        activity: Activity,
        nativeAdId: String,
        actionLoaded: (() -> Unit)? = null,
        actionFailed: (() -> Unit)? = null
    ) {
        val videoOptions = VideoOptions.Builder().build()
        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
        val adLoader = AdLoader.Builder(
            activity,
            nativeAdId
        )
            .forNativeAd { ad: NativeAd ->
                adState = AdState.LOADED
                //nativeAd?.destroy()
                nativeAd = ad
                countDownTimer.start()
                Log.d("native", "preloadNative: ${nativeAd.hashCode()}")
                actionLoaded?.invoke()
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adState = AdState.FAILED
                    actionFailed?.invoke()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adState = AdState.LOADED
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    adState = AdState.IMPRESSION
                }
            })
            .withNativeAdOptions(
                adOptions
            )
            .build()

        adState = AdState.LOADING
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAdView(
        activity: Activity,
        nativeAdLayout: Int,
        nativeAd: NativeAd?,
        container: ConstraintLayout?,
        frameLayout: FrameLayout,
        shimmerFrameLayout: FrameLayout
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeAd?.let {
                val inflater =
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val mainAdView = inflater.inflate(nativeAdLayout, null)
                val adView=mainAdView.findViewById<NativeAdView>(R.id.ad_view)
                // Set the media view.
                adView.mediaView = adView.findViewById(R.id.ad_media)

                // Set other ad assets.
                adView.headlineView = adView.findViewById(R.id.ad_headline)
                adView.bodyView = adView.findViewById(R.id.ad_body)
                adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
                adView.iconView = adView.findViewById(R.id.ad_app_icon)
//                adView.priceView = adView.findViewById(R.id.ad_price)
//                adView.starRatingView = adView.findViewById(R.id.ad_stars)
//                adView.storeView = adView.findViewById(R.id.ad_store)
//                adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

                // The headline and media content are guaranteed to be in every UnifiedNativeAd.
                (adView.headlineView as TextView).text = nativeAd.headline
                nativeAd.mediaContent?.let { adView.mediaView?.setMediaContent(it) }

                // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
                // check before trying to display them.
                if (nativeAd.body == null) {
                    adView.bodyView?.visibility = View.INVISIBLE
                } else {
                    adView.bodyView?.visibility = View.VISIBLE
                    (adView.bodyView as TextView).text = nativeAd.body
                }

                if (nativeAd.callToAction == null) {
                    adView.callToActionView?.visibility = View.INVISIBLE
                } else {
                    adView.callToActionView?.visibility = View.VISIBLE
                    (adView.callToActionView as Button).text = nativeAd.callToAction
                }

                if (nativeAd.icon == null) {
                    adView.iconView?.visibility = View.INVISIBLE
                } else {
                    adView.iconView?.let {
                        (it as ImageView).setImageDrawable(
                            nativeAd.icon?.drawable
                        )
                        adView.iconView?.visibility = View.VISIBLE
                    }

                }

//                if (nativeAd.price == null) {
//                    adView.priceView?.visibility = View.INVISIBLE
//                } else {
//                    adView.priceView?.visibility = View.VISIBLE
//                    (adView.priceView as TextView).text = nativeAd.price
//                }

//                if (nativeAd.store == null) {
//                    adView.storeView?.visibility = View.INVISIBLE
//                } else {
//                    adView.storeView?.visibility = View.VISIBLE
//                    (adView.storeView as TextView).text = nativeAd.store
//                }

//                if (nativeAd.starRating == null) {
//                    adView.starRatingView?.visibility = View.INVISIBLE
//                } else {
//                    (adView.starRatingView as RatingBar).rating =
//                        nativeAd.starRating!!.toFloat()
//                    adView.starRatingView?.visibility = View.VISIBLE
//                }

//                if (nativeAd.advertiser == null) {
//                    adView.advertiserView?.visibility = View.GONE
//                } else {
//                    (adView.advertiserView as TextView).text = nativeAd.advertiser
//                    adView.advertiserView?.visibility = View.GONE
//                }

                adView.setNativeAd(nativeAd)
                container?.show()
                frameLayout.show()
                shimmerFrameLayout.hide()
                frameLayout.removeAllViews()
                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(mainAdView)
                }
                frameLayout.addView(mainAdView)
            } ?: run {
                container?.hide()
                frameLayout.hide()
                shimmerFrameLayout.hide()
            }
        }
    }
}