package com.climax.ads.adsclas.applovin

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.isNetworkAvailable
import com.facebook.shimmer.ShimmerFrameLayout
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.climax.ads.adsclas.ShimmerBox


class AppLovinBannerAd() {
    private var adView: MaxAdView? = null
    private var TAG = "AdaptiveBanner"


    fun loadApplovinBanner(
        context: Context,
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
                        loadingText.visibility = View.GONE
                    }

                    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                        adLayout.visibility = View.GONE
                        loadingText.visibility = View.GONE
                    }
                })
            }

        } else {
            onShowAdCompletedAction?.invoke(false)
            adLayout.visibility = View.GONE
            loadingText.visibility = View.GONE
        }
    }


}
@Composable
fun loadApplovinBannerCompose(context: Context,
    modifier: Modifier = Modifier,
    bannerId: String,
    onAdLoaded: ((Boolean) -> Unit)? = null
) {

    var isAdLoaded by remember { mutableStateOf(false) }

    // Decide banner height based on screen size
    val screenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val bannerHeightDp = if (screenWidthDp >= 600) 90 else 50

    val adView = remember {
        MaxAdView(bannerId, context).apply {
            setListener(object : MaxAdViewAdListener {
                override fun onAdExpanded(ad: MaxAd) {}
                override fun onAdCollapsed(ad: MaxAd) {}

                override fun onAdLoaded(ad: MaxAd) {
                    isAdLoaded = true
                    onAdLoaded?.invoke(true)
                }

                override fun onAdDisplayed(ad: MaxAd) {}
                override fun onAdHidden(ad: MaxAd) {}

                override fun onAdClicked(ad: MaxAd) {
                    Constants.isOnClickAnyAd = true
                }

                override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                    isAdLoaded = false
                    onAdLoaded?.invoke(false)
                }

                override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                    isAdLoaded = false
                    onAdLoaded?.invoke(false)
                }
            })
        }
    }

    // Trigger load only once
    LaunchedEffect(Unit) {
        if (!Constants.isPurchased() && context.isNetworkAvailable()) {
            adView.loadAd()
        } else {
            onAdLoaded?.invoke(false)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(bannerHeightDp.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isAdLoaded) {
            AndroidView(
                factory = { adView },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
            )
        }
    }
}