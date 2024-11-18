package com.example.ads.ads

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.example.ads.ads.enums.AdState

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

import kotlinx.coroutines.*

const val LOG_TAG = "Fahad_Ads"

class AppOpen {
    private var adState = AdState.LOAD
    private var appOpenAd: AppOpenAd? = null
    private var loadingDialog: Dialog? = null
    private var action: (() -> Unit)? = null
    private var userWaitingJob: Job? = null
    private var currentActivityRegisterCheck: String = ""
    private var requestedForAd = false
    var lastAppOpenId: String = ""

    init {
        Log.e("Splash","init App Open")
        adState= AdState.LOAD
    }
    fun loadAd(
        activity: Activity,
        appOpenId: String = Constants.splashAppOpenId,
        onShowAdCompletedAction: ((Boolean) -> Unit)? = null
    ) {
        Log.e("Splash", "start loadAd")
        lastAppOpenId = appOpenId
        if (isAdAvailable()) {
            Log.e("Splash", "callback from isAdAvailable")
            onShowAdCompletedAction?.invoke(true)
            return
        }
        if (AdState.LOADING == adState) {
            Log.e("Splash", "callback from AdState.LOADING")
            onShowAdCompletedAction?.invoke(false)
            return
        }
        Log.e("AppOpen", "Loading New App Open Ad - ${Constants.splashAppOpenId}")
        adState = AdState.LOADING
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            activity,
            appOpenId,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {

                    Log.e("", "onAdLoaded")
                    Log.e("Splash", "callback from onAdLoaded")
                    Constants.isSplashAppOpenFail = false
                    appOpenAd = ad
                    adState = AdState.LOADED
                    onShowAdCompletedAction?.invoke(true)
                  //  Toast.makeText(activity, "onAdLoaded", Toast.LENGTH_SHORT).show()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("AppOpen", "onAdFailedToLoad")
                    Log.e("Splash", "callback from onAdFailedToLoad")
                    Constants.isSplashAppOpenFail = true
                    adState = AdState.FAILED
                    onShowAdCompletedAction?.invoke(false)
                }
            }
        )
    }

    private fun loadAdWithWaiting(activity: Activity, onShowAdCompletedAction: () -> Unit) {
        if (AdState.LOADING == adState || isAdAvailable()) {
            return
        }

        adState = AdState.LOADING
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            activity,
            lastAppOpenId,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    adState = AdState.LOADED
                    Constants.isSplashAppOpenFail = false
                    appOpenAd?.apply {
                        setAdListeners(onShowAdCompletedAction)
                        show(activity)
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    adState = AdState.FAILED
                    Constants.isSplashAppOpenFail = true
                }
            }
        )
    }

    private fun setAdListeners(onShowAdCompletedAction: () -> Unit) {
        appOpenAd?.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Constants.isAppOpenShowed = true
                    appOpenAd = null
                    requestedForAd = false
                    adState = AdState.DISMISSED
                    Constants.OTHER_AD_DISPLAYED = false
                    onShowAdCompletedAction.invoke()

                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    adState = AdState.SHOWN_FAILED
                    Constants.OTHER_AD_DISPLAYED = false
                    appOpenAd=null
                    onShowAdCompletedAction.invoke()
                }

                override fun onAdShowedFullScreenContent() {
                    Constants.OTHER_AD_DISPLAYED = true
                    adState = AdState.SHOWING
                }
            }
        }
    }

    private fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }

    fun isShowingAppOpen(): Boolean {
        if (adState == AdState.SHOWING) {
            return true
        }
        return false
    }

    fun showAppOpenAd(
        activity: Activity,
        waitingTime: Long = 6000L,
        onShowAdCompletedAction: () -> Unit,
        showLoadingDialog: Boolean = false
    ) {
        requestedForAd = true

        if (adState == AdState.SHOWING) {
            Log.e("AppOpen", "showAppOpenAd class ${adState}")
            return
        }
        if (Constants.OTHER_AD_DISPLAYED) {
            Log.e(LOG_TAG, "The other ad is already showing.")
            return
        }

        appOpenAd?.apply {
            Log.e("AppOpen", "showAppOpenAd show now ${adState}")
            setAdListeners(onShowAdCompletedAction)
            show(activity)
        } ?: run {
//            loadingDialog = activity.createLoadingDialog("Loading Ad...!")
            try {
              /*  if (showLoadingDialog) loadingDialog =
                    activity.createLoadingDialog("Processing...!")
                if (!activity.isFinishing && !activity.isDestroyed && showLoadingDialog) {
                    loadingDialog?.show()
                }*/
                loadAdWithWaiting(activity, onShowAdCompletedAction)
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
                        AdState.LOADING -> onShowAdCompletedAction.invoke()
                        AdState.LOADED -> {
                            setAdListeners(onShowAdCompletedAction)
                            appOpenAd?.show(activity) ?: run { onShowAdCompletedAction.invoke() }
                        }

                        AdState.FAILED -> onShowAdCompletedAction.invoke()
                        AdState.SHOWN_FAILED -> onShowAdCompletedAction.invoke()
                        AdState.SHOWING -> {}
                        AdState.DISMISSED -> {
                            requestedForAd = false
                        }

                        AdState.IMPRESSION -> {}
                        AdState.AD_CLICKED -> {}
                    }
                }
            } catch (e: Exception) {
                Log.e("AppOpen", "ExceptionIntoDisplay: ${e.printStackTrace()}")
            }

        }
    }

    fun onResume(activity: Activity) {
        if (currentActivityRegisterCheck == activity.localClassName && requestedForAd) {
            userWaitingJob = CoroutineScope(Dispatchers.Main).launch {
                delay(2000L)
                when (adState) {
                    AdState.LOAD -> {
                        action?.let { loadAdWithWaiting(activity, it) }
                    }

                    AdState.LOADING -> action?.invoke()
                    AdState.LOADED -> {
                        action?.let {
                            setAdListeners(it)
                            appOpenAd?.show(activity)
                        }
                    }

                    AdState.FAILED -> action?.invoke()
                    AdState.SHOWN_FAILED -> {
                        action?.let {
                            setAdListeners(it)
                            appOpenAd?.show(activity)
                        }
                    }

                    AdState.SHOWING -> {}
                    AdState.DISMISSED -> {}
                    AdState.IMPRESSION -> {}
                    AdState.AD_CLICKED -> {}
                }
            }
        } else {
            if (!activity.isFinishing && !activity.isDestroyed) {
                try {
                    currentActivityRegisterCheck = activity.localClassName
                    loadingDialog?.apply { if (isShowing) dismiss() }
                } catch (e: Exception) {

                }
            }
        }
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

    fun isAppOpenAdAvailable(): Boolean {
        return appOpenAd != null
    }
}