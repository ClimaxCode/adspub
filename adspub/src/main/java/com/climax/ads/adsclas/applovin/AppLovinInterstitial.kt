package com.climax.ads.adsclas.applovin

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.ads.MaxInterstitialAd
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.enums.AdState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppLovinInterstitial {

    private var appLovinInterstitialAd: MaxInterstitialAd? = null
    private var adState = AdState.LOAD
    private var loadingDialog: Dialog? = null
    private var userWaitingJob: Job? = null
    private var action: (() -> Unit)? = null
    private var preLoad = false
    private var requestedForAd = false
    private var currentActivityRegisterCheck: String = ""
    private var lastInterstitialAdId = ""

    companion object {
        var discardDone = false
    }

    init {
        Log.e("AppLovinInterstitial", "Creating interstitial class")
    }

    fun loadInterstitial(
        context: Activity,
        interstitialAdId: String,
        onShowAdCompletedAction: ((Boolean) -> Unit)? = null
    ) {
        if (Constants.applovinIntersId != "" && Constants.applovinIntersId != "0") {
            lastInterstitialAdId = interstitialAdId

            if (adState == AdState.LOADING) return
            appLovinInterstitialAd?.let {
                // Already loaded
            } ?: run {
                Log.e("AppLovinInterstitial", "Loading new interstitial ad: $lastInterstitialAdId")
                adState = AdState.LOADING

                appLovinInterstitialAd = MaxInterstitialAd(Constants.applovinIntersId)
                appLovinInterstitialAd?.setListener(object : MaxAdListener {
                    override fun onAdLoaded(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad loaded successfully.")
                        adState = AdState.LOADED
                        Constants.isLoadedAdInters = true
                        onShowAdCompletedAction?.invoke(true)
                    }

                    override fun onAdDisplayed(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad displayed.")
                    }

                    override fun onAdHidden(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad hidden.")
                        adState = AdState.DISMISSED
                        requestedForAd = false
                        Constants.isLoadedAdInters = false
                        appLovinInterstitialAd = null
                        onShowAdCompletedAction?.invoke(false)
                        Log.d(
                            "AppLovinInterstitial",
                            "onAdHidden: ${Constants.applovinIntersId}"
                        )
                        if (Constants.isApplovinEnabled){
                            Constants.interstitialAppLovinNew.loadInterstitial(context,Constants.applovinIntersId)
                        }else{
                            Log.d("onAppLovin", "onAppLovin:Disabled Inter")
                        }
                    }

                    override fun onAdClicked(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad clicked.")
                        Constants.isOnClickAnyAd = true
                    }

                    override fun onAdLoadFailed(
                        adUnitId: String,
                        error: com.applovin.mediation.MaxError
                    ) {
                        Log.e("AppLovinInterstitial", "Ad failed to load: ${error.message}")
                        adState = AdState.FAILED
                        Constants.isLoadedAdInters = false
                        onShowAdCompletedAction?.invoke(false)
                    }

                    override fun onAdDisplayFailed(
                        ad: MaxAd,
                        error: com.applovin.mediation.MaxError
                    ) {
                        Log.e("AppLovinInterstitial", "Ad failed to display: ${error.message}")
                        adState = AdState.SHOWN_FAILED
                        onShowAdCompletedAction?.invoke(false)
                    }
                })
                appLovinInterstitialAd?.loadAd()
            }
        }
    }

    fun showInterstitial(
        activity: Activity,
        interstitialAdId: String,
        preLoad: Boolean,
        waitingTime: Long,
        onShowAdCompletedAction: () -> Unit,
        onInterstitialFailed: ((Boolean) -> Unit?)? = null
    ) {
        Log.e("AppLovinInterstitial", "showInterstitial")
        if (adState == AdState.SHOWING) return

        appLovinInterstitialAd?.apply {
            if (isReady) {
                // Set the listener with different callbacks
                setListener(object : MaxAdListener {
                    override fun onAdLoaded(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad loaded successfully.")
                        adState = AdState.SHOWING
                        //showAd()
                    }

                    override fun onAdDisplayed(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad displayed.")
                        adState = AdState.SHOWING
                    }

                    override fun onAdClicked(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad clicked.")
                        // Handle ad click action if necessary
                    }

                    override fun onAdLoadFailed(adUnitId: String, error: com.applovin.mediation.MaxError) {
                        Log.e("AppLovinInterstitial", "Ad failed to load: ${error.message}")
                        adState = AdState.FAILED
                        Constants.isLoadedAdInters = false
                        onInterstitialFailed?.invoke(false)
                    }

                    override fun onAdDisplayFailed(ad: MaxAd, error: com.applovin.mediation.MaxError) {
                        Log.e("AppLovinInterstitial", "Ad failed to display: ${error.message}")
                        adState = AdState.SHOWN_FAILED
                        onInterstitialFailed?.invoke(false)
                    }

                    override fun onAdHidden(ad: MaxAd) {
                        Log.e("AppLovinInterstitial", "Ad hidden.")
                        adState = AdState.DISMISSED
                        Constants.isLoadedAdInters = false
                        appLovinInterstitialAd = null
                        onShowAdCompletedAction?.invoke()
                        Log.d("AppLovinInterstitial", "onAdHidden: ${Constants.applovinIntersId}")
                        if (Constants.isApplovinEnabled){
                            Constants.interstitialAppLovinNew.loadInterstitial(activity,Constants.applovinIntersId)
                        }else{
                            Log.d("onAppLovin", "onAppLovin:Disabled Inter")
                        }
                    }
                })
                showAd()
            } else {
                if (Constants.isApplovinEnabled){
                    Constants.interstitialAppLovinNew.loadInterstitial(activity,Constants.applovinIntersId)
                }else{
                    Log.d("onAppLovin", "onAppLovin:Disabled Inter")
                }
                onShowAdCompletedAction?.invoke()
            }
        } ?: run {
            Log.e("AppLovinInterstitial", "Ad object is null. Load ad first.")
            onShowAdCompletedAction?.invoke()
        }
    }

    fun dismissLoadingDialog(activity: Activity) {
        loadingDialog?.apply {
            if (isShowing && !activity.isFinishing && !activity.isDestroyed) {
                try {
                    dismiss()
                } catch (e: IllegalArgumentException) {
                    // Handle or log
                } catch (e: Exception) {
                    // Handle or log
                } finally {
                    loadingDialog = null
                }
            }
        }
    }
    private fun loadInterstitialWithWaiting(
        activity: Activity,
        interstitialAdId: String,
        preLoad: Boolean,
        onShowAdCompletedAction: () -> Unit,
        onInterstitialFailed: (() -> Unit?)? = null
    ) {
        if (Constants.applovinIntersId != "" && Constants.applovinIntersId != "0") {

            appLovinInterstitialAd = MaxInterstitialAd(Constants.applovinIntersId, activity)
            appLovinInterstitialAd?.setListener(object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd) {
                    Log.e("AppLovinInterstitial", "Ad loaded successfully.")
                    adState = AdState.LOADED
                    Constants.isLoadedAdInters = true
                    onShowAdCompletedAction.invoke()

                    adState = AdState.LOADED
                    setInterstitialAdListeners(
                        activity,
                        interstitialAdId,
                        preLoad,
                        onShowAdCompletedAction,
                        onInterstitialFailed
                    )
                    Constants.isFailInterstitialAd = false
                }

                override fun onAdDisplayed(ad: MaxAd) {
                    Log.e("AppLovinInterstitial", "Ad displayed.")
                }

                override fun onAdHidden(ad: MaxAd) {
                    Log.e("AppLovinInterstitial", "Ad hidden.")
                    adState = AdState.DISMISSED
                    requestedForAd = false
                    Constants.isLoadedAdInters = false
                    appLovinInterstitialAd = null
                    onShowAdCompletedAction?.invoke()
                    //  loadInterstitial(activity, lastInterstitialAdId, onShowAdCompletedAction)
                }

                override fun onAdClicked(ad: MaxAd) {
                    Log.e("AppLovinInterstitial", "Ad clicked.")
                }

                override fun onAdLoadFailed(
                    adUnitId: String,
                    error: com.applovin.mediation.MaxError
                ) {
                    Log.e("AppLovinInterstitial", "Ad failed to load: ${error.message}")
                    adState = AdState.FAILED
                    Constants.isLoadedAdInters = false
                    onInterstitialFailed?.invoke()
                }

                override fun onAdDisplayFailed(ad: MaxAd, error: com.applovin.mediation.MaxError) {
                    Log.e("AppLovinInterstitial", "Ad failed to display: ${error.message}")
                    adState = AdState.SHOWN_FAILED
                    onInterstitialFailed?.invoke()
                }
            })
            appLovinInterstitialAd?.loadAd()
        }
    }
    fun onResume(activity: Activity) {
        if (currentActivityRegisterCheck == activity.localClassName && requestedForAd) {
            userWaitingJob = CoroutineScope(Dispatchers.Main).launch {
                delay(1000L)
                when (adState) {
                    AdState.LOAD -> {
                        action?.let {
                            loadInterstitialWithWaiting(
                                activity,
                                lastInterstitialAdId,
                                preLoad,
                                it
                            )
                        }
                    }

                    AdState.LOADING -> action?.invoke()
                    AdState.LOADED -> {
                        action?.let {
                            setInterstitialAdListeners(activity, lastInterstitialAdId, preLoad, it)
                            appLovinInterstitialAd?.showAd() ?: it.invoke()
                        }
                    }

                    AdState.FAILED -> action?.invoke()
                    AdState.SHOWN_FAILED -> {
                        action?.let {
                            setInterstitialAdListeners(activity, lastInterstitialAdId, preLoad, it)
                            appLovinInterstitialAd?.showAd() ?: it.invoke()
                        }
                    }

                    AdState.SHOWING -> {}
                    AdState.DISMISSED -> {}
                    AdState.IMPRESSION -> {}
                    AdState.AD_CLICKED -> {}
                }
            }
        } else {
            currentActivityRegisterCheck = activity.localClassName
            loadingDialog?.apply { if (isShowing) dismiss() }
        }
    }
    private fun setInterstitialAdListeners(
        activity: Activity,
        interstitialAdId: String,
        preLoad: Boolean,
        onShowAdCompletedAction: () -> Unit,
        onInterstitialFailed: (() -> Unit?)? = null
    ) {

        appLovinInterstitialAd?.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                Log.e("AppLovinInterstitial", "Ad loaded successfully.")
                adState = AdState.LOADED
                Constants.isLoadedAdInters = true
                onShowAdCompletedAction?.invoke()
            }

            override fun onAdDisplayed(ad: MaxAd) {
                Constants.OTHER_AD_DISPLAYED = true
                Log.e("AppLovinInterstitial", "Ad displayed.")
            }

            override fun onAdHidden(ad: MaxAd) {
                Log.e("AppLovinInterstitial", "Ad hidden.")
                adState = AdState.DISMISSED
                requestedForAd = false
                Constants.isLoadedAdInters = false
                onShowAdCompletedAction?.invoke()
                appLovinInterstitialAd = null
                Constants.OTHER_AD_DISPLAYED = false
               // loadInterstitial(activity, lastInterstitialAdId, onShowAdCompletedAction)
            }

            override fun onAdClicked(ad: MaxAd) {
                Constants.isOnClickAnyAd = true
                Log.e("AppLovinInterstitial", "Ad clicked.")
            }

            override fun onAdLoadFailed(adUnitId: String, error: com.applovin.mediation.MaxError) {
                Log.e("AppLovinInterstitial", "Ad failed to load: ${error.message}")
                adState = AdState.SHOWN_FAILED
                Constants.OTHER_AD_DISPLAYED = false
                onInterstitialFailed?.invoke()
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: com.applovin.mediation.MaxError) {
                Log.e("AppLovinInterstitial", "Ad failed to display: ${error.message}")
                adState = AdState.SHOWN_FAILED
                Constants.OTHER_AD_DISPLAYED = false
                onInterstitialFailed?.invoke()
            }
        })

    }


    fun onPause() {
        loadingDialog?.apply { if (isShowing) dismiss() }
        userWaitingJob?.cancel()
    }

    fun destroyAd() {
        appLovinInterstitialAd?.destroy()
        appLovinInterstitialAd = null
    }
}
