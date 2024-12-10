package com.climax.ads.adsclas.helpers

import android.app.Activity
import android.widget.FrameLayout
import com.climax.ads.R
import com.climax.ads.adsclas.AdaptiveBannerAd
import com.climax.ads.adsclas.CollapsibleBannerAd
import com.climax.ads.adsclas.InlineBannerAdManager
import com.facebook.shimmer.ShimmerFrameLayout


fun Activity?.collapsibleBanner(
    adId: String
) {
    var bannerAd = CollapsibleBannerAd()
    bannerAd.loadBanner(
        this!!,
        adId,
        this.findViewById(R.id.bannerAdLayout),
        this.findViewById(R.id.shimmerBanner)
    )
}

fun Activity?.inlineBanner(
    adId: String
) {
    var admobBannerAdManager = InlineBannerAdManager()
    admobBannerAdManager.loadBannerAd(
        adId,
        this!!,
        this.findViewById(R.id.bannerAdLayoutinline),
        this.findViewById(R.id.shimmerInline)
    )
}

fun Activity?.adaptiveBanner(
    adId: String
) {

    var admobBannerAdManager = AdaptiveBannerAd(this!!)
    admobBannerAdManager.loadAdaptiveBanner(
        adId,
        this.findViewById(R.id.bannerAdLayout),
        this.findViewById(R.id.shimmerBanner)
    )

}