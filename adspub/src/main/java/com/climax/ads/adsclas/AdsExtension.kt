package com.climax.ads.adsclas

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.climax.ads.R
import com.climax.ads.adsclas.Constants.ADS_INITIALIZATION_COMPLETED
import com.climax.ads.adsclas.Constants.OTHER_AD_DISPLAYED
import com.climax.ads.adsclas.Constants.appOpen
import com.climax.ads.adsclas.Constants.clickCount
import com.climax.ads.adsclas.Constants.interstitial
import com.climax.ads.adsclas.Constants.interstitialAdCount
import com.climax.ads.adsclas.Constants.isOnClickAnyAd
import com.climax.ads.adsclas.Constants.largeNative
import com.climax.ads.adsclas.Constants.native
import com.climax.ads.adsclas.Constants.rewarded
import com.climax.ads.adsclas.Constants.showFullNative
import com.climax.ads.adsclas.Constants.smallNative
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.MobileAds
import com.google.android.material.textview.MaterialTextView


/* AppOpen Ad Extension functions*/
fun Activity?.loadAppOpen(
    appOpenId: String,
    onShowAdCompletedAction: ((Boolean) -> Unit)? = null
) {
    this?.let {
        if (!Constants.isPurchased()) {
            appOpen.loadAd(this, appOpenId, onShowAdCompletedAction)
        } else {
            onShowAdCompletedAction?.invoke(false)
        }
    }
}
fun Activity.loadPreInterstitialSplash(interstitialAdId: String,onShowAdCompletedAction: ((Boolean) -> Unit)? = null) {
    if (!Constants.isPurchased()) {
        interstitial.loadInterstitial(this, interstitialAdId,onShowAdCompletedAction)
    }
}


fun Activity?.showAppOpen(
    waitingTime: Long = 0L,
    showLoadingDialog: Boolean = false,
    onShowAdCompletedAction: () -> Unit,
) {
    this?.let {
        if (!Constants.isPurchased()) {
            Log.e("AppOpen", "Ext showAppOpen ${!OTHER_AD_DISPLAYED}")
            if (!OTHER_AD_DISPLAYED && Constants.appIsForeground) {
                if (!isOnClickAnyAd) {
                    appOpen.showAppOpenAd(
                        this,
                        waitingTime,
                        onShowAdCompletedAction,
                        showLoadingDialog
                    )
                } else {
                    isOnClickAnyAd = false
                }

            } else {
                onShowAdCompletedAction.invoke()
            }
        } else onShowAdCompletedAction.invoke()
    } ?: run {
        onShowAdCompletedAction.invoke()
    }
}

fun Activity?.onResumeAppOpen() {
    this?.let { appOpen.onResume(this) }
}

fun Activity?.onPauseAppOpen() {
    this?.let { appOpen.onPause() }
}

fun Activity?.checkAndShowInterstitial(
    onlyShowAdMob: Boolean = true,
    interstitialAdId: String,
    preLoad: Boolean = false,
    waitingTime: Long = 0L,
    onShowAdCompletedAction: () -> Unit,
    onInterstitialFailed: (() -> Unit)
) {

    Log.d("zh", "checkAndShowInterstitial: checkAppOpen ${Constants.isAppOpenShowed}")
    // Log.d("zh", "checkAndShowInterstitial: onlyShowAdmob ${onlyShowAdMob}")
    if (!Constants.isAppOpenShowed && isNetworkAvailable()) {
        if (!onlyShowAdMob) {
            Log.e("ads", "checkAndShowInterstitial $interstitialAdCount")
        } else {
            Log.e("ads", "checkAndShowInterstitial admob")
            showInterstitial(
                interstitialAdId,
                preLoad,
                waitingTime,
                onShowAdCompletedAction,
                onInterstitialFailed
            )
        }

    } else {
        Log.e("ads", "Last ad was App Open")
        Constants.isAppOpenShowed = false
        onInterstitialFailed.invoke()
    }

}


fun Activity?.checkAndShowInterstitialConvertorScreen(
    onlyShowAdMob: Boolean = true,
    interstitialAdId: String,
    preLoad: Boolean = false,
    waitingTime: Long = 0L,
    onShowAdCompletedAction: () -> Unit,
    onInterstitialFailed: (() -> Unit)
) {

    Log.d(
        "zh",
        "checkAndShowInterstitialConvertorScreen: intersAdConvertScreenCount ${Constants.intersAdConvertScreenCount}"
    )
    // Log.d("zh", "checkAndShowInterstitial: onlyShowAdmob ${onlyShowAdMob}")
    if (!Constants.isAppOpenShowed) {
        if (!onlyShowAdMob) {
            Log.e("ads", "checkAndShowInterstitialConvertorScreen $interstitialAdCount")

        } else {
            if (!Constants.isPurchased()) {
                if (!OTHER_AD_DISPLAYED) {
                    showInterstitial(
                        interstitialAdId,
                        preLoad,
                        waitingTime,
                        onShowAdCompletedAction = {
                            onShowAdCompletedAction.invoke()
                        },
                        onInterstitialFailed = {

                        }
                    )

                } else {
                    onInterstitialFailed.invoke()
                }
            } else {
                //   Constants.interstitialAdCount++
                onShowAdCompletedAction.invoke()
            }
            Log.e("ads", "checkAndShowInterstitialConvertorScreen admob")


        }
    } else {
        Log.e("ads", "Last ad was App Open")
        Constants.isAppOpenShowed = false
        onInterstitialFailed.invoke()
    }

}


/* interstitial Ad Extension functions*/
fun Activity?.showInterstitial(
    interstitialAdId: String,
    preLoad: Boolean = false,
    waitingTime: Long = 0L,
    onShowAdCompletedAction: () -> Unit,
    onInterstitialFailed: (() -> Unit)? = null
) {
    this?.let {
        Log.e("ad", "showInterstitial/ ${!Constants.isPurchased()}")
        if (!Constants.isPurchased()) {
            Log.e("ad", "showInterstitial/  ${OTHER_AD_DISPLAYED}")
            if (!OTHER_AD_DISPLAYED) {
                Constants.isLastAdWasAdmob = true
                Log.e(
                    "ad",
                    "showInterstitial/ $interstitialAdCount interstitial.showInterstitial} ${Constants.isLastAdWasAdmob}"
                )
                Log.e("ad", "showInterstitial/  ${Constants.isFailInterstitialAd}")

                interstitial.showInterstitial(
                    this, interstitialAdId,
                    preLoad,
                    waitingTime,
                    onShowAdCompletedAction,
                    onInterstitialFailed
                )

            } else {
                Constants.isLastAdWasAdmob = false
                onInterstitialFailed?.invoke()
            }
        } else {
            Constants.isLastAdWasAdmob = true
            //   Constants.interstitialAdCount++
            onShowAdCompletedAction.invoke()
        }

    } ?: run {
        Constants.isLastAdWasAdmob = true
        //Constants.interstitialAdCount++
        onShowAdCompletedAction.invoke()
    }
}

fun Activity?.interstitialOnPause() {
    interstitial.onPause()
}

fun Activity.loadPreInterstitial(interstitialAdId: String) {
    if (!Constants.isPurchased()) {
        interstitial.loadInterstitial(this, interstitialAdId)
    }
}


fun Activity.hideNavigationBar() {
    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
}

fun canLoadInterstitialAd(): Boolean {
    Log.e("InterstitialNew", "canLoadInterstitialAd count is : ${clickCount}")
    clickCount.let {
        var newCount = it + 1
        return newCount % 3 == 0
    }
    return false
}


/* native Ads implementation */

fun Activity?.showNative(
    nativeAdId: String,
    nativeAdLayout: Int,
    container: ConstraintLayout?,
    frameLayout: FrameLayout,
    shimmerFrameLayout: FrameLayout,
    preLoad: Boolean = false,
    actionLoaded: () -> Unit,
    actionFailed: () -> Unit,
) {
    this?.let {
        if (!Constants.isPurchased() && isNetworkAvailable()) {
            Log.d("zh", "showNative: $ADS_INITIALIZATION_COMPLETED")
            native.showNative(
                this,
                nativeAdLayout,
                nativeAdId,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
            if (preLoad) native.preLoadNative(this)
        } else {
            MobileAds.initialize(application) { ADS_INITIALIZATION_COMPLETED = true }
            container?.hide()
            frameLayout.hide()
            shimmerFrameLayout.hide()
        }
    } ?: run {
        container?.hide()
        frameLayout.hide()
        shimmerFrameLayout.hide()
    }
}


/* native Ads implementation */

fun Activity?.showSmallNative(
    nativeAdLayout: Int,
    container: ConstraintLayout?,
    frameLayout: FrameLayout,
    shimmerFrameLayout: FrameLayout,
    preLoad: Boolean = false,
    actionLoaded: () -> Unit,
    actionFailed: () -> Unit,
) {
    this?.let {
        if (!Constants.isPurchased() && isNetworkAvailable()) {
            if (ADS_INITIALIZATION_COMPLETED) {
                smallNative.showNative(
                    this,
                    nativeAdLayout,
                    container,
                    frameLayout,
                    shimmerFrameLayout,
                    actionLoaded,
                    actionFailed
                )
                if (preLoad) smallNative.preLoadNative(this)
            } else {
                MobileAds.initialize(application) { ADS_INITIALIZATION_COMPLETED = true }
                container?.hide()
                frameLayout.hide()
                shimmerFrameLayout.hide()
            }
        } else {
            container?.hide()
            frameLayout.hide()
            shimmerFrameLayout.hide()
        }
    } ?: run {
        container?.hide()
        frameLayout.hide()
        shimmerFrameLayout.hide()
    }
}

fun Activity?.preLoadNativeAd(
    nativeAdId: String, actionLoaded: () -> Unit,
    actionFailed: () -> Unit,
) {
    this?.let {
        smallNative.preLoadNative(this)
    }
}

fun Activity?.preLoadLargeNativeAd(nativeAdId: String) {
    this?.let {
        if (!Constants.isPurchased() && isNetworkAvailable()) {
            Log.e("Aqeel", "Pre load exit Native")
            largeNative.preLoadNative(it, nativeAdId)
        }
    }
}

fun Activity?.callNativeAd(

    nativeAdId: String,
    nativeAdtype: String,
    preLoad: Boolean = false,
    loadNewAd: Boolean = true,
    actionLoaded: (() -> Unit),
    actionFailed: () -> Unit,
    tryToShowAgain: (Boolean) -> Unit,
    actionButtonColor: Int,
    actionButtonTextColor: Int,
    bgColor: Int
) {

    var type: Int = 0
    var frameLayout: FrameLayout? = null
    var shimmer: ShimmerFrameLayout? = null
    Log.d("Ads", "callNativeAd: $nativeAdtype")
    when (nativeAdtype) {
        "large" -> {
            type = R.layout.full_native
            frameLayout = this?.findViewById(R.id.adContainer)!!
            shimmer = this.findViewById(R.id.shimmmmer)!!
        }

        "native1" -> {
            type = R.layout.native1
            frameLayout = this?.findViewById(R.id.adContainer1)!!
            shimmer = this.findViewById(R.id.shimmer1)!!
        }

        "native2" -> {
            type = R.layout.native2
            frameLayout = this?.findViewById(R.id.adContainer2)!!
            shimmer = this.findViewById(R.id.shimmer2)!!
        }

        "native3" -> {
            type = R.layout.native3
            frameLayout = this?.findViewById(R.id.adContainer3)!!
            shimmer = this.findViewById(R.id.shimmer3)!!
        }

        "native4" -> {
            type = R.layout.native4
            frameLayout = this?.findViewById(R.id.adContainer4)!!
            shimmer = this.findViewById(R.id.shimmer4)!!
        }

        "native5" -> {
            type = R.layout.native5
            frameLayout = this?.findViewById(R.id.adContainer5)!!
            shimmer = this.findViewById(R.id.shimmer5)!!
        }

        "native6" -> {
            type = R.layout.native6
            frameLayout = this?.findViewById(R.id.adContainer6)!!
            shimmer = this.findViewById(R.id.shimmer6)!!
        }

        "small" -> {
            type = R.layout.small_native
            frameLayout = this?.findViewById(R.id.adContainers)!!
            shimmer = this.findViewById(R.id.shimmers)!!
        }

        else -> {
            return
        }
//        "exit1" ->{
//            type = R.layout.exit_native1_adcontent
//            frameLayout =  this?.findViewById(R.id.adContainere1)!!
//            shimmer=  this.findViewById(R.id.shimmerExit1)!!
//        }
//        "exit2"->{
//            type = R.layout.exit_native2_adcontent
//            frameLayout =  this?.findViewById(R.id.adContainere2)!!
//            shimmer=  this.findViewById(R.id.shimmerExit2)!!
//        }
    }
    Log.d("Ads", "callNativeAd: $type")
    showLargeNative(
        nativeAdId,
        type,
        this.findViewById(R.id.ad_root),
        frameLayout,
        shimmer,
        preLoad,
        loadNewAd,
        actionLoaded,
        actionFailed,
        tryToShowAgain,
        actionButtonColor,
        actionButtonTextColor,
        bgColor
    )
}

fun Activity?.callFullNativeAd(
    nativeAdId: String,
    preLoad: Boolean = false,
    loadNewAd: Boolean = true,
    actionLoaded: (() -> Unit),
    actionFailed: () -> Unit,
    tryToShowAgain: (Boolean) -> Unit,
    actionButtonColor: Int,
    actionButtonTextColor: Int,
    bgColor: Int
) {

    var type: Int = 0
    var frameLayout: FrameLayout? = null
    var shimmer: ShimmerFrameLayout? = null

    type = R.layout.full_native
    frameLayout = this?.findViewById(R.id.adContainer)!!
    shimmer = this.findViewById(R.id.shimmmmer)!!

    Log.d("Ads", "callNativeAd: $type")
    showLargeNative(
        nativeAdId,
        type,
        this.findViewById(R.id.ad_root_full_native),
        frameLayout,
        shimmer,
        preLoad,
        loadNewAd,
        actionLoaded,
        actionFailed,
        tryToShowAgain,
        actionButtonColor,
        actionButtonTextColor,
        bgColor
    )
}
fun Activity?.showLargeNative(
    nativeAdId: String,
    nativeAdLayout: Int,
    container: ConstraintLayout?,
    frameLayout: FrameLayout,
    shimmerFrameLayout: FrameLayout,
    preLoad: Boolean = false,
    loadNewAd: Boolean = true,
    actionLoaded: () -> Unit,
    actionFailed: () -> Unit,
    tryToShowAgain: (Boolean) -> Unit,
    actionButtonColor: Int,
    actionButtonTextColor: Int,
    bgColor: Int
) {
    this?.let {
        if (!Constants.isPurchased() && isNetworkAvailable()) {
            largeNative.showNative(
                this,
                nativeAdId = nativeAdId,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                loadNewAd,
                actionLoaded,
                actionFailed,
                tryToShowAgain,
                actionButtonColor,
                actionButtonTextColor,
                bgColor
            )
            if (preLoad) largeNative.preLoadNative(this, nativeAdId)
        } else {
            container?.hide()
            frameLayout.hide()
            shimmerFrameLayout.hide()
        }
    } ?: run {
        container?.hide()
        frameLayout.hide()
        shimmerFrameLayout.hide()
    }
}

fun Context?.isNetworkAvailable(): Boolean {
    this?.let {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkCapabilities = cm?.activeNetwork ?: return false
        val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> true
        }
    }
    return false
}


fun canShowInterstitialAd(): Boolean {
    if (Constants.isAppOpenShowed) {
        Constants.isAppOpenShowed = false
        return false
    }
    return true
}

fun Activity.createLoadingDialog(text: String) = Dialog(this).apply {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setCancelable(false)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    // val binding = LoadingviewBinding.inflate(layoutInflater)
//    setContentView(binding.root)
    setContentView(R.layout.load_ad_dialog)
    findViewById<MaterialTextView>(R.id.loadingTxt).text = text
}

fun Activity?.showShowFullNative(
    nativeAdId: String,
    nativeAdLayout: Int,
    container: ConstraintLayout?,
    frameLayout: FrameLayout,
    shimmerFrameLayout: FrameLayout,
    preLoad: Boolean = false,
    actionLoaded: () -> Unit,
    actionFailed: () -> Unit,
) {
    this?.let {
        if (!Constants.isPurchased() && isNetworkAvailable()) {
            showFullNative.showPreLoadNative(
                nativeAdId,
                this,
                nativeAdLayout,
                container,
                frameLayout,
                shimmerFrameLayout,
                actionLoaded,
                actionFailed
            )
        } else {
            container?.hide()
            frameLayout.hide()
            shimmerFrameLayout.hide()
        }
    } ?: run {
        container?.hide()
        frameLayout.hide()
        shimmerFrameLayout.hide()
    }
}

fun Activity?.preLoadFullNativeAd(nativeAdId: String) {
    this?.let {
        if (!Constants.isPurchased() && isNetworkAvailable()) {
            Log.e("Aqeel", "Pre load exit Native")
            showFullNative.preLoadNative(it, nativeAdId)
        }
    }
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Activity?.showRewarded(
    preLoad: Boolean = false,
    waitingTime: Long = 8000L,
    adId: String,
    dontShowAnyDialog: Boolean = false,
    showSavingDialog: Boolean = false,
    onShowAdCompletedAction: () -> Unit,
    onFailedAdAction: () -> Unit,
) {
    if (!Constants.isPurchased()) {
        this?.let {
            if (isNetworkAvailable()) {
                if (ADS_INITIALIZATION_COMPLETED) {
                    rewarded.showRewarded(
                        this,
                        adId,
                        preLoad,
                        waitingTime,
                        showSavingDialog,
                        dontShowAnyDialog,
                        onShowAdCompletedAction,
                        onFailedAdAction
                    )
                } else {
                    MobileAds.initialize(application) { ADS_INITIALIZATION_COMPLETED = true }
                    onFailedAdAction.invoke()
                }
            } else onFailedAdAction.invoke()
        } ?: run {
            onFailedAdAction.invoke()
        }
    } else {
        onShowAdCompletedAction.invoke()
    }
}

fun Activity?.preLoadRewardedVideos(
    activity: Activity,
    adId:String,
    showLoadingDialog: Boolean = false,
    onRewardedAdLoaded: ((Boolean) -> Unit)?
) {
    if (!Constants.isPurchased()) {
        this?.let {
            rewarded.loadRewarded(activity,adId, showLoadingDialog, onRewardedAdLoaded)
        } ?: run {
            onRewardedAdLoaded?.invoke(false)
        }
    } else onRewardedAdLoaded?.invoke(true)
}

//fun Activity?.preLoadRewardedVideo(
//    showLoadingDialog: Boolean = false,
//    onRewardedAdLoaded: ((Boolean) -> Unit)?
//) {
//    if (!Constants.isPurchased()) {
//        this?.let {
//            rewarded.loadRewarded(this, showLoadingDialog, onRewardedAdLoaded)
//        } ?: run {
//            onRewardedAdLoaded?.invoke(false)
//        }
//    } else onRewardedAdLoaded?.invoke(true)
//}


fun AppCompatActivity.showLoadingDialog(): AlertDialog {
    val alertDialog = AlertDialog.Builder(this)
    alertDialog.setView(R.layout.load_ad_dialog)
    val loadingAdDialog = alertDialog.create()
    loadingAdDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    loadingAdDialog.show()
    loadingAdDialog.setCanceledOnTouchOutside(false)
    loadingAdDialog.setCancelable(false)
    return loadingAdDialog
}

fun AlertDialog.dismissLoadingDialog() {
    if (this.isShowing) {
        this.dismiss()
    }
}

class OnSingleClickListener(private val isAdmobClick: Boolean, private val block: () -> Unit) :
    View.OnClickListener {

    companion object {
        private var lastClickTime = 0L
    }

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 600) {
            return
        }
//        checkAndUpdateClickCount()
        if (!isAdmobClick) {
            if (Constants.isAppOpenShowed) {
                Constants.isAppOpenShowed = false
            }
        }
        lastClickTime = SystemClock.elapsedRealtime()

        block()
    }
}

fun View.setOnSingleClickListener(isAdmobClick: Boolean = false, block: () -> Unit) {

    setOnClickListener(OnSingleClickListener(isAdmobClick, block))
}

fun Activity.openUrl(appUri: Uri) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, appUri))
    } catch (e: Exception) {
    }
}