package com.climax.ads.ads


import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.gms.ads.*


class InlineBannerAdForList {
    companion object {
        const val BANNER_MANAGER_TAG = "InlineBannerAdForList"
    }

    var adView: AdView? = null

    fun loadBannerAd(context: Activity?,  onShowAdCompletedAction: (Boolean) -> Unit) {
        if (!Constants.isPurchased()) {
            context?.let {
                Log.e(BANNER_MANAGER_TAG, "LoadNewBannerAd")

                if (adView == null) {
                    adView = AdView(context).apply {
                        adUnitId = Constants.inlineBannerId
                        setAdSize(getAdSize(context))
                    }
                }
                val adRequest = AdRequest.Builder().build()
                adView?.loadAd(adRequest)

                adView?.adListener = object : AdListener() {
                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                        Log.e(BANNER_MANAGER_TAG, "onAdFailedToLoad:${p0.message}")
                        onShowAdCompletedAction.invoke(false)
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.e(BANNER_MANAGER_TAG, "onAdLoaded")
                        onShowAdCompletedAction.invoke(true)
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        Log.e(BANNER_MANAGER_TAG, "AdImpression")
                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
                    }
                }

            }
        } else {

        }
    }

    fun showInlineBannerAd( adContainerView: FrameLayout?, shimmerFrameLayout: TextView){
        adView?.let { ad ->
            (ad.parent as? ViewGroup)?.removeView(ad)

            adContainerView?.let { container ->
                container.removeAllViews()
                container.addView(ad)
                container.show()
            }
        }?:run {
            adContainerView?.hide()
            shimmerFrameLayout?.hide()
        }
    }

    fun removeBannerAdView(adContainerView: FrameLayout?) {
        try {
            adView?.let {
                if (it.parent != null) {
                    (it.parent as ViewGroup).removeView(it)
                }
                adContainerView?.removeAllViews()
            }
        } catch (e: Exception) {
            Log.e(BANNER_MANAGER_TAG, "ExceptionInAdUnBinding")
        }
    }

    private fun getAdSize(context: Activity): AdSize {
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

        return AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)
    }

    fun onPauseBannerAd() {
        Log.e(BANNER_MANAGER_TAG, "OnPauseBannerAd")
        adView?.pause()
    }

    fun onResumeBannerAd() {
        Log.e(BANNER_MANAGER_TAG, "onResumeBannerAd")
        adView?.resume()
    }

    fun destroyBannerAd() {
        adView?.destroy()
    }
}

