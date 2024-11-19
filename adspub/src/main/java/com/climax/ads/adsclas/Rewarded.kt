package com.climax.ads.adsclas

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.climax.ads.adsclas.Constants.OTHER_AD_DISPLAYED
import com.climax.ads.adsclas.enums.AdState

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Rewarded {
    private var mRewardedAd: RewardedAd? = null
    private var adState = AdState.LOAD
    private var loadingDialog: Dialog? = null
    private var userWaitingJob: Job? = null
    private var action: (() -> Unit)? = null
    private var preLoad = false
    private var currentActivityRegisterCheck: String = ""
    private var requestedForAd = false
    private var isRewardGranted = false
    fun loadRewarded(
        activity: Activity,
        showLoadingDialog: Boolean = false,
        onRewardedAdLoaded: ((Boolean) -> Unit)?
    ) {
        dismissLoadingDialog(activity)
        if (showLoadingDialog) {
            loadingDialog?.let {
                if (!it.isShowing) {
                    activity.createLoadingDialog("Processing...!")
                } else loadingDialog
            } ?: kotlin.run {
                loadingDialog= activity.createLoadingDialog("Processing...!")
            }
            loadingDialog?.apply {
                if (!isShowing) {
                    loadingDialog?.show()
                }
            }
        }
        mRewardedAd?.let {
            dismissLoadingDialog(activity)
            onRewardedAdLoaded?.invoke(true)
        } ?: run {
            val adRequest = AdRequest.Builder().build()
            adState = AdState.LOADING

            RewardedAd.load(activity,
                Constants.REWARDED_VIDEO_ADMOB_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("RewardedVideo", "onAdFailedToLoad: ${adError.message}")
                        adState = AdState.FAILED
                        mRewardedAd = null
                        dismissLoadingDialog(activity)
                        onRewardedAdLoaded?.invoke(false)
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Log.d("RewardedVideo", "onAdLoaded:")
                        adState = AdState.LOADED
                        mRewardedAd = rewardedAd
                        dismissLoadingDialog(activity)
                        onRewardedAdLoaded?.invoke(true)
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

    private fun loadRewardedWithWaiting(
        activity: Activity, preLoad: Boolean,
    ) {
        mRewardedAd?.let { } ?: run {
            val adRequest = AdRequest.Builder().build()
            adState = AdState.LOADING

            RewardedAd.load(activity,
                Constants.REWARDED_VIDEO_ADMOB_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("RewardedVideo", "onAdFailedToLoad: ${adError.message}")
                        adState = AdState.FAILED
                        mRewardedAd = null
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Log.d("RewardedVideo", "onAdLoaded: ${rewardedAd.rewardItem}")
                        adState = AdState.LOADED
                        mRewardedAd = rewardedAd
                        //setRewardedAdListeners(activity, preLoad,onShowAdCompletedAction)
                    }
                })
        }
    }

    private fun setRewardedAdListeners(
        activity: Activity, preLoad: Boolean, onShowAdCompletedAction: (() -> Unit)? = null,
    ) {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {}
            override fun onAdDismissedFullScreenContent() {
                adState = AdState.DISMISSED
                Constants.OTHER_AD_DISPLAYED = false
                requestedForAd = false
                if (isRewardGranted) {
                    isRewardGranted = false
                    onShowAdCompletedAction?.invoke()
                }
                mRewardedAd = null

                currentActivityRegisterCheck = ""
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.d("RewardedVideo", "LoadFailed: ${adError.message}")
                adState = AdState.SHOWN_FAILED
            }

            override fun onAdImpression() {
                adState = AdState.IMPRESSION
            }

            override fun onAdShowedFullScreenContent() {
                adState = AdState.SHOWING
                OTHER_AD_DISPLAYED = true
            }
        }
    }

    fun showRewarded(
        activity: Activity,
        preLoad: Boolean,
        waitingTime: Long,
        showSavingDialog: Boolean,
        dontShowAnyDialog: Boolean,
        onShowAdCompletedAction: (() -> Unit)?,
        onFailedAdAction: () -> Unit,
    ) {
        try {
            this@Rewarded.preLoad = preLoad
            requestedForAd = true
            if (adState == AdState.SHOWING) {
                return
            }

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
            mRewardedAd?.apply {
                setRewardedAdListeners(activity, preLoad, onShowAdCompletedAction)
                show(activity) {
                    requestedForAd = false
                    isRewardGranted = true
                }
            } ?: run {
                loadingDialog = if (showSavingDialog) {
                    activity.createLoadingDialog("Saving File")
                } else {
                    loadingDialog?.let {
                        if (!it.isShowing) {
                            activity.createLoadingDialog("Processing...!")
                        } else loadingDialog
                    } ?: kotlin.run {
                        activity.createLoadingDialog("Processing...!")
                    }

                }

                if (!dontShowAnyDialog && !activity.isFinishing && !activity.isDestroyed) {
                    loadingDialog?.apply {
                        if (!isShowing) {
                            loadingDialog?.show()
                        }
                    }
                }
                loadRewardedWithWaiting(activity, preLoad)
                action = onShowAdCompletedAction
                userWaitingJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(waitingTime)
                    Log.d("RewardedVideo", "showRewarded: adState = $adState")

                    when (adState) {
                        AdState.LOAD -> {}
                        AdState.LOADING -> {
                            Log.e("Rewarded", "LOADING")
                            requestedForAd = false
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
                            onFailedAdAction.invoke()

                        }

                        AdState.LOADED -> {
                            loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                            setRewardedAdListeners(activity, preLoad, onShowAdCompletedAction)
                            mRewardedAd?.show(activity) {
                                requestedForAd = false
                                isRewardGranted = true
                            } ?: run {
                                requestedForAd = false
                                loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                                onFailedAdAction.invoke()
                            }
                        }

                        AdState.FAILED -> {
                            requestedForAd = true
                            loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                            onFailedAdAction.invoke()
                        }

                        AdState.SHOWN_FAILED -> {
                            Log.e("Rewarded", "ShowFailed")
                            loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                            setRewardedAdListeners(activity, preLoad, onShowAdCompletedAction)
                            mRewardedAd?.show(activity) {
                                requestedForAd = false
                                isRewardGranted = true
                            } ?: run {
                                requestedForAd = false
                                loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                                onFailedAdAction.invoke()
                            }
                        }

                        AdState.SHOWING -> {
                            loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                        }

                        AdState.DISMISSED -> {
                            loadingDialog?.apply { if (isShowing && !activity.isFinishing && !activity.isDestroyed) dismiss() }
                            requestedForAd = false
                        }

                        AdState.IMPRESSION -> {}
                        AdState.AD_CLICKED -> {}

                    }
                }
            }
        } catch (e: Exception) {
            onFailedAdAction.invoke()
        }
    }

    fun isRewardedAdAvailable(): Boolean {
        return mRewardedAd != null
    }

//    fun onResume(activity: Activity, currentClassName: String) {
//        if (currentActivityRegisterCheck == currentClassName && requestedForAd) {
//            userWaitingJob = CoroutineScope(Dispatchers.Main).launch {
//                delay(2000L)
//                when (adState) {
//                    AdState.LOAD -> {
//                        action?.let { loadRewardedWithWaiting(activity, preLoad) }
//                    }
//                    AdState.LOADING -> action?.invoke()
//                    AdState.LOADED -> {
//                        action?.let {
//                            setRewardedAdListeners(activity, preLoad,null)
//                            setRewardedAdListeners(activity, preLoad,null)
//                            mRewardedAd?.show(activity) { _ -> it.invoke() } ?: it.invoke()
//                        }
//                    }
//                    AdState.FAILED -> action?.invoke()
//                    AdState.SHOWN_FAILED -> {
//                        action?.let {
//                            setRewardedAdListeners(activity, preLoad)
//                            mRewardedAd?.show(activity) { _ -> it.invoke() } ?: it.invoke()
//                        }
//                    }
//                    AdState.SHOWING -> {}
//                    AdState.DISMISSED -> {}
//                    AdState.IMPRESSION -> {}
//                    AdState.AD_CLICKED -> {}
//
//                }
//            }
//        } else {
//            currentActivityRegisterCheck = currentClassName
//            loadingDialog?.apply { if (isShowing) dismiss() }
//        }
//    }

    fun onPause() {
        loadingDialog?.apply { if (isShowing) dismiss() }
        userWaitingJob?.cancel()
    }
}