package com.climax.ads.adsclas.applovin

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.createLoadingDialog
import com.climax.ads.adsclas.enums.AdState

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RewardedAppLovin {
    private var mRewardedAd: MaxRewardedAd? = null
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

        if (Constants.applovinRewardedId.isEmpty() && Constants.applovinRewardedId != "0") {
            Log.e("RewardedAppLovin", "Invalid ad unit ID")
            onRewardedAdLoaded?.invoke(false)
            return
        }

        if (showLoadingDialog) {
            loadingDialog?.let {
                if (!it.isShowing) {
                    activity.createLoadingDialog("Processing...!")
                } else loadingDialog
            } ?: kotlin.run {
                loadingDialog = activity.createLoadingDialog("Processing...!")
            }
            loadingDialog?.apply {
                if (!isShowing) {
                    loadingDialog?.show()
                }
            }
        }

        try {
            if (mRewardedAd?.isReady == true) {
                dismissLoadingDialog(activity)
                onRewardedAdLoaded?.invoke(true)
            } else {
                adState = AdState.LOADING

                // Ensure the ad unit ID is valid before creating the ad instance
                mRewardedAd = MaxRewardedAd.getInstance(Constants.applovinRewardedId, activity)
                mRewardedAd?.setListener(object : MaxRewardedAdListener {
                    override fun onAdLoaded(ad: MaxAd) {
                        Log.d("RewardedAppLovin", "Ad loaded successfully")
                        adState = AdState.LOADED
                        dismissLoadingDialog(activity)
                    }

                    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                        Log.d("RewardedAppLovin", "Failed to load ad: ${error?.message}")
                        adState = AdState.FAILED
                        dismissLoadingDialog(activity)
                    }

                    override fun onAdDisplayed(ad: MaxAd) {
                        Log.d("RewardedAppLovin", "Ad displayed")
                        adState = AdState.SHOWING
                        Constants.OTHER_AD_DISPLAYED = true
                    }

                    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                        Log.d("RewardedAppLovin", "Failed to display ad: ${error?.message}")
                        adState = AdState.SHOWN_FAILED
                    }

                    override fun onAdHidden(ad: MaxAd) {
                        Log.d("RewardedAppLovin", "Ad dismissed")
                        adState = AdState.DISMISSED
                        Constants.OTHER_AD_DISPLAYED = false
                        requestedForAd = false
                        if (isRewardGranted) {
                            isRewardGranted = false
                            action?.invoke()
                        }
                    }

                    override fun onAdClicked(ad: MaxAd) {
                        Log.d("RewardedAppLovin", "Ad clicked")
                        adState = AdState.AD_CLICKED
                        Constants.isOnClickAnyAd = true
                    }

                    override fun onUserRewarded(ad: MaxAd, reward: MaxReward) {
                        Log.d("RewardedAppLovin", "User rewarded")
                        isRewardGranted = true
                    }
                })

                mRewardedAd?.loadAd()
            }
        } catch (e: IllegalArgumentException) {
            Log.e("RewardedAppLovin", "IllegalArgumentException: ${e.message}")
            onRewardedAdLoaded?.invoke(false)  // Notify failure due to exception
        } catch (e: Exception) {
            Log.e("RewardedAppLovin", "Unexpected error: ${e.message}")
            onRewardedAdLoaded?.invoke(false)
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
            this@RewardedAppLovin.preLoad = preLoad
            requestedForAd = true
            if (adState == AdState.SHOWING) {
                return
            }

            dismissLoadingDialog(activity)

            if (mRewardedAd?.isReady == true) {
                mRewardedAd?.showAd()
                action = onShowAdCompletedAction
            } else {
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
                loadRewarded(activity, false) {
                    if (!it) {
                        onFailedAdAction.invoke()
                    }
                }

                userWaitingJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(waitingTime)
                    if (adState != AdState.LOADED) {
                        dismissLoadingDialog(activity)
                        onFailedAdAction.invoke()
                    }
                }
            }
        } catch (e: Exception) {
            onFailedAdAction.invoke()
        }
    }

    fun isRewardedAdAvailable(): Boolean {
        return mRewardedAd?.isReady == true
    }

    fun onPause() {
        loadingDialog?.apply { if (isShowing) dismiss() }
        userWaitingJob?.cancel()
    }
}
