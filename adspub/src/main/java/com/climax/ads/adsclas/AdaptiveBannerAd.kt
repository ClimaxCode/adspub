package com.climax.ads.ads

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdaptiveBannerAd(var context: Context) {
    private lateinit var adView: AdView
    private var TAG = "AdaptiveBanner"

    fun loadAdaptiveBanner(containerLayout : ConstraintLayout, adLayout: FrameLayout, bannerId: String,loadingText :TextView,onShowAdCompletedAction: ((Boolean) -> Unit)?=null) {
        if (!Constants.isPurchased() && context.isNetworkAvailable()) {
            adView = AdView(context)

            val adLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

            adView.layoutParams = adLayoutParams

            adLayout.addView(adView)

            adView.adUnitId = bannerId


            val display = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            display.defaultDisplay.getMetrics(outMetrics)


            val density = outMetrics.density

            var adWidthPixels = adLayout.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

            adView.setAdSize(adSize)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

            adView.adListener = object : AdListener() {
                override fun onAdClicked() {
                    Log.d(TAG, "onAdClicked: ")
                }

                override fun onAdClosed() {
                    Log.d(TAG, "onAdClosed: ")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "onAdFailedToLoad: ")
                    onShowAdCompletedAction?.invoke(false)
                }

                override fun onAdImpression() {
                    Log.d(TAG, "onAdImpression: ")
                }

                override fun onAdLoaded() {
                    Log.d(TAG, "onAdLoaded: ")
                    onShowAdCompletedAction?.invoke(true)
                }

                override fun onAdOpened() {
                    Log.d(TAG, "onAdOpened: ")
                }
            }
        }else{
            onShowAdCompletedAction?.invoke(false)
            adLayout.visibility = View.GONE
            containerLayout.visibility = View.GONE
            loadingText.visibility = View.GONE

        }
    }

}