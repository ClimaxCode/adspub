package com.example.ads.ads

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.example.ads.ads.Constants.isFailInterstitialAd
import com.example.ads.ads.enums.AdState
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class Interstitial {
    private var mInterstitialAd: InterstitialAd? = null
    private var adState = AdState.LOAD
    private var loadingDialog: Dialog? = null
    private var userWaitingJob: Job? = null
    private var action: (() -> Unit)? = null
    private var preLoad = false
    private var currentActivityRegisterCheck: String = ""
    private var requestedForAd = false
    var lastInterstitialAdId = ""

    companion object {
        var discardDone = false
    }
    init {
        Log.e("InterstitialNew", "Creating interstitial class")
    }

    fun loadInterstitial(context: Activity, interstitialAdId: String) {
        if (interstitialAdId.isNotEmpty()) lastInterstitialAdId = interstitialAdId
        if (adState == AdState.LOADING) return
        mInterstitialAd?.let {

        } ?: run {
            Log.e("InterstitialNew", "Loading new Interstitial$lastInterstitialAdId")
            val adRequest = AdRequest.Builder().build()
            adState = AdState.LOADING
            InterstitialAd.load(context.applicationContext,
                lastInterstitialAdId,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.e("InterstitialNew", "onAdFailedToLoad")
                        Constants.isLoadedAdInters = false
                        adState = AdState.FAILED
                        mInterstitialAd = null
                        isFailInterstitialAd = true

                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.e("InterstitialNew", "onAdLoaded")
                        Constants.isLoadedAdInters = true
                        adState = AdState.LOADED
                        mInterstitialAd = interstitialAd
                        isFailInterstitialAd = false

                    }
                })
        }
    }

    fun dismissLoadingDialog(activity: Activity) {
        loadingDialog?.apply {
            if (isShowing && !activity.isFinishing && !activity.isDestroyed) {
                try {
                    dismiss()
                } catch (e: IllegalArgumentException) {
                    // Handle or log or ignore
                } catch (e: Exception) {
                    // Handle or log or ignore
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
        mInterstitialAd?.let {

        } ?: run {
            val adRequest = AdRequest.Builder().build()
            adState = AdState.LOADING
            InterstitialAd.load(activity.applicationContext,
                interstitialAdId,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adState = AdState.FAILED
                        mInterstitialAd = null
                        isFailInterstitialAd = true
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        adState = AdState.LOADED
                        mInterstitialAd = interstitialAd
                        setInterstitialAdListeners(
                            activity,
                            interstitialAdId,
                            preLoad,
                            onShowAdCompletedAction,
                            onInterstitialFailed
                        )
                        isFailInterstitialAd = false
                    }
                })
        }
    }

    private fun setInterstitialAdListeners(
        activity: Activity,
        interstitialAdId: String,
        preLoad: Boolean,
        onShowAdCompletedAction: () -> Unit,
        onInterstitialFailed: (() -> Unit?)? = null
    ) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {}
            override fun onAdDismissedFullScreenContent() {
                discardDone = true
                adState = AdState.DISMISSED
                requestedForAd = false

                Constants.OTHER_AD_DISPLAYED = false
                Constants.isInterstitialShowed = true
                Constants.interstitialAdCount++
                onShowAdCompletedAction.invoke()
                mInterstitialAd = null
                Constants.isLoadedAdInters = false

            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                adState = AdState.SHOWN_FAILED
                Constants.OTHER_AD_DISPLAYED = false
                onInterstitialFailed?.invoke()
            }

            override fun onAdImpression() {
                adState = AdState.IMPRESSION
            }

            override fun onAdShowedFullScreenContent() {
                adState = AdState.SHOWING
                Constants.OTHER_AD_DISPLAYED = true
            }
        }
    }

    fun showInterstitial(
        activity: Activity,
        interstitialAdId: String,
        preLoad: Boolean,
        waitingTime: Long,
        onShowAdCompletedAction: () -> Unit,
        onInterstitialFailed: (() -> Unit?)? = null
    ) {

        Log.e("InterstitialNew", "showInterstitial")
        this@Interstitial.preLoad = preLoad
        lastInterstitialAdId = interstitialAdId
        requestedForAd = true
        if (adState == AdState.SHOWING) {
            return
        }
        mInterstitialAd?.apply {
            Constants.OTHER_AD_DISPLAYED = true
            Log.e("InterstitialNew", "showInterstitial called show")
            setInterstitialAdListeners(activity, interstitialAdId, preLoad, onShowAdCompletedAction)
            show(activity)

        } ?: run {
//            loadingDialog = activity.createLoadingDialog("Loading Ad...!")
            if (preLoad) {
                loadingDialog = activity.createLoadingDialog("Processing...!")
                if (!activity.isFinishing && !activity.isDestroyed) {
                    loadingDialog?.show()
                }
                loadInterstitialWithWaiting(
                    activity,
                    interstitialAdId,
                    preLoad,
                    onShowAdCompletedAction
                )
                action = onShowAdCompletedAction
                userWaitingJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(waitingTime)
                    loadingDialog?.apply {
                        if (!activity.isFinishing && !activity.isDestroyed) {
                            if (isShowing) dismiss()
                        }
                    }
                    when (adState) {
                        AdState.LOAD -> {}
                        AdState.LOADING -> onInterstitialFailed?.invoke()
                        AdState.LOADED -> {
                            setInterstitialAdListeners(
                                activity,
                                interstitialAdId,
                                preLoad,
                                onShowAdCompletedAction
                            )
                            mInterstitialAd?.show(activity)
                                ?: run { onShowAdCompletedAction.invoke() }
                        }

                        AdState.FAILED -> onInterstitialFailed?.invoke()
                        AdState.SHOWN_FAILED -> {
                            setInterstitialAdListeners(
                                activity,
                                interstitialAdId,
                                preLoad,
                                onShowAdCompletedAction
                            )
                            mInterstitialAd?.show(activity)
                                ?: run { onShowAdCompletedAction.invoke() }
                        }

                        AdState.SHOWING -> {}
                        AdState.DISMISSED -> {
                            requestedForAd = false
                        }

                        AdState.IMPRESSION -> {}
                        AdState.AD_CLICKED -> {}
                    }
                }
            }else{
                onInterstitialFailed?.invoke()
            }

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
                            mInterstitialAd?.show(activity) ?: it.invoke()
                        }
                    }

                    AdState.FAILED -> action?.invoke()
                    AdState.SHOWN_FAILED -> {
                        action?.let {
                            setInterstitialAdListeners(activity, lastInterstitialAdId, preLoad, it)
                            mInterstitialAd?.show(activity) ?: it.invoke()
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

    fun onPause() {
        loadingDialog?.apply { if (isShowing) dismiss() }
        userWaitingJob?.cancel()
    }
}