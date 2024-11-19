package com.climax.ads.adsclas.helpers

import android.app.Activity
import android.widget.FrameLayout
import com.climax.ads.adsclas.AdaptiveBannerAd
import com.climax.ads.adsclas.CollapsibleBannerAd
import com.climax.ads.adsclas.InlineBannerAdManager
import com.facebook.shimmer.ShimmerFrameLayout


fun Activity?.collapsibleBanner(
    adId: String,
    adContainerView: FrameLayout,
    shimmerFrameLayout: ShimmerFrameLayout
) {
    var bannerAd = CollapsibleBannerAd()
    bannerAd.loadBanner(
        this!!,
        adId,
        adContainerView,
        shimmerFrameLayout
    )
}

fun Activity?.inlineBanner(
    adId: String,
    adContainerView: FrameLayout,
    shimmerFrameLayout: ShimmerFrameLayout
) {
    var admobBannerAdManager = InlineBannerAdManager()
    admobBannerAdManager.loadBannerAd(
        adId,
        this!!,
        adContainerView,
        shimmerFrameLayout
    )
}

fun Activity?.adaptiveBanner(
    adId: String,
    adContainerView: FrameLayout,
    shimmerFrameLayout: ShimmerFrameLayout
) {
    var admobBannerAdManager = AdaptiveBannerAd(this!!)
    admobBannerAdManager.loadAdaptiveBanner(
        adId,
        adContainerView,
        shimmerFrameLayout
    )

}