package com.climax.ads.adsclas.applovin

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.isNetworkAvailable

import com.facebook.shimmer.ShimmerFrameLayout



class AppLovinBannerAd(var context: Activity) {
    private var adView: MaxAdView? = null
    private var TAG = "AdaptiveBanner"



    fun loadApplovinBanner(
        containerLayout: ConstraintLayout,
        adLayout: FrameLayout,
        bannerId: String,
        loadingText: ShimmerFrameLayout,
        onShowAdCompletedAction: ((Boolean) -> Unit)? = null
    ) {
        if (!Constants.isPurchased() && context.isNetworkAvailable()) {
            if (Constants.applovinBannerId != "" && Constants.applovinBannerId != "0") {

                adView = MaxAdView(bannerId, context)
                val bannerHeightDp =
                    if (context.resources.configuration.smallestScreenWidthDp >= 600) {
                        90
                    } else {
                        50
                    }

                val bannerHeightPx =
                    (bannerHeightDp * context.resources.displayMetrics.density).toInt()

                val adLayoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    bannerHeightPx
                )

                adView?.layoutParams = adLayoutParams

                adLayout.addView(adView)
                adView?.loadAd()
                adView!!.setListener(object : MaxAdViewAdListener {
                    override fun onAdExpanded(ad: MaxAd) {}
                    override fun onAdCollapsed(ad: MaxAd) {}
                    override fun onAdLoaded(ad: MaxAd) {
                        loadingText.visibility = View.GONE
                    }

                    override fun onAdDisplayed(ad: MaxAd) {}
                    override fun onAdHidden(ad: MaxAd) {}
                    override fun onAdClicked(ad: MaxAd) {
                        Constants.isOnClickAnyAd = true
                    }

                    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                        adLayout.visibility = View.GONE
                        containerLayout.visibility = View.GONE
                        loadingText.visibility = View.GONE
                    }

                    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                        adLayout.visibility = View.GONE
                        containerLayout.visibility = View.GONE
                        loadingText.visibility = View.GONE
                    }
                })
            }

        } else {
            onShowAdCompletedAction?.invoke(false)
            adLayout.visibility = View.GONE
            containerLayout.visibility = View.GONE
            loadingText.visibility = View.GONE
        }
    }


}