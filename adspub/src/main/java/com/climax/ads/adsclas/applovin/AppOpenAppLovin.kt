package com.climax.ads.adsclas.applovin

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.applovin.mediation.ads.MaxAppOpenAd
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.enums.AdState
import kotlinx.coroutines.*

const val LOG_TAG = "appOpenAPpLovin"

class AppOpenAppLovin {
    private var adState = AdState.LOAD
    private lateinit var appOpenAd: MaxAppOpenAd
    private var loadingDialog: Dialog? = null
    private var action: (() -> Unit)? = null
    private var userWaitingJob: Job? = null
    private var currentActivityRegisterCheck: String = ""
    private var requestedForAd = false
    var lastAppOpenId: String = ""

    init {
        Log.e(LOG_TAG, "init App Open")
        adState = AdState.LOAD
    }

    fun loadAd(
        activity: Activity,
        appOpenId: String = Constants.applovinAppOpenId,
        onShowAdCompletedAction: ((Boolean) -> Unit)? = null
    ) {
        if (Constants.applovinAppOpenId != "" && Constants.applovinAppOpenId!= "0") {
            Log.e(LOG_TAG, "start loadAd")
         //   lastAppOpenId = appOpenId

            if (isAdAvailable()) {
                Log.e(LOG_TAG, "callback from isAdAvailable")
                onShowAdCompletedAction?.invoke(true)
                return
            }

            if (AdState.LOADING == adState) {
                Log.e(LOG_TAG, "callback from AdState.LOADING")
                onShowAdCompletedAction?.invoke(false)
                return
            }

            Log.e("AppOpen", "Loading New App Open Ad - $appOpenId")
            adState = AdState.LOADING
            appOpenAd = MaxAppOpenAd(appOpenId, activity)

            appOpenAd.setListener(object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd) {
                    Log.e(LOG_TAG, "onAdLoaded")
                    adState = AdState.LOADED
                    Constants.isSplashAppOpenFail = false
                    onShowAdCompletedAction?.invoke(true)
                }

                override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                    Log.e(LOG_TAG, "onAdLoadFailed: ${error?.message}")
                    adState = AdState.FAILED
                    Constants.isSplashAppOpenFail = true
                    onShowAdCompletedAction?.invoke(false)
                }


                override fun onAdDisplayed(ad: MaxAd) {
                    Log.e(LOG_TAG, "onAdDisplayed")
                    adState = AdState.SHOWING
                    Constants.OTHER_AD_DISPLAYED = true
                }

                override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                    Log.e(LOG_TAG, "onAdDisplayFailed: ${error?.message}")
                    adState = AdState.SHOWN_FAILED
                    Constants.OTHER_AD_DISPLAYED = false
                    onShowAdCompletedAction?.invoke(false)
                }

                override fun onAdHidden(ad: MaxAd) {
                    Log.e(LOG_TAG, "onAdHidden")
                    adState = AdState.DISMISSED
                    Constants.OTHER_AD_DISPLAYED = false
                    appOpenAd = MaxAppOpenAd(appOpenId, activity)
                    onShowAdCompletedAction?.invoke(true)
                }

                override fun onAdClicked(ad: MaxAd) {
                    Log.e(LOG_TAG, "onAdClicked")
                    adState = AdState.AD_CLICKED
                    Constants.isOnClickAnyAd = true
                }
            })

            appOpenAd.loadAd()
        }
    }

    fun showAppOpenAd(
        activity: Activity,
        waitingTime: Long = 6000L,
        onShowAdCompletedAction: () -> Unit,
        showLoadingDialog: Boolean = false
    ) {
        requestedForAd = true

        if (adState == AdState.SHOWING) {
            Log.e(LOG_TAG, "Ad is already showing.")
            return
        }

        if (!isAdAvailable()) {
            Log.e(LOG_TAG, "Ad is not available to show.")
            onShowAdCompletedAction.invoke()
            return
        }

        Log.e(LOG_TAG, "Showing App Open Ad")
        appOpenAd.showAd()
    }

    private fun isAdAvailable(): Boolean {
        return this::appOpenAd.isInitialized && appOpenAd.isReady
    }

    fun onPause() {
        loadingDialog?.ownerActivity?.let {
            it.runOnUiThread {
                if (!it.isFinishing && !it.isDestroyed)
                    loadingDialog?.apply { if (isShowing) dismiss() }
            }
        }
        userWaitingJob?.cancel()
    }
}
