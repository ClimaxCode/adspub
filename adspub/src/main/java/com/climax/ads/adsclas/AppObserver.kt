package com.climax.ads.adsclas

import android.app.Activity
import android.app.Application
import android.app.LauncherActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.climax.ads.adsclas.Constants.appIsForeground


class AppObserver(
    private val appOpen: AppOpen,
) : LifecycleEventObserver,
    Application.ActivityLifecycleCallbacks {

    private var mCurrentActivity: Activity? = null

    private fun onBackgroundEntered() {
        mCurrentActivity?.let {
            if (it !is LauncherActivity && !Constants.isOpenLocationDialog) {
                it.loadAppOpen(Constants.onResumeAppOpenId)
            }
           /* if (it is HomeScreenActivity) {
                if (!it.isShowLocation) {
                    it.loadAppOpen(Constants.onResumeAppOpenId)
                }
            } else {
                if (it !is FullscreenActivity && !Constants.isOpenLocationDialog && it !is IAPActivity && it !is OnboardingScreen) {
                    it.loadAppOpen(Constants.onResumeAppOpenId)
                }
            }
*/
        }
        appIsForeground = false
    }

    private fun onForegroundEntered() {
        appIsForeground = true
        if (!Constants.isPurchased()) {
            mCurrentActivity?.let {
            //    if (it !is LauncherActivity && !Constants.isOpenLocationDialog && it !is PremiumScreen ) {
                if (it !is LauncherActivity && !Constants.isOpenLocationDialog) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        it.showAppOpen(0L, onShowAdCompletedAction = {})
                    }, 1000L)
                }
            /*    if (it is HomeScreenActivity) {
                    if (!it.isShowLocation) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            it.showAppOpen(0L, onShowAdCompletedAction = {})
                        }, 1000L)
                    }
                } else {
                    if (it !is LauncherActivity && !Constants.isOpenLocationDialog && it !is IAPActivity ) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            it.showAppOpen(0L, onShowAdCompletedAction = {})
                        }, 1000L)
                    }
                }*/

                /*   if (it is MainScreen) {
                       if (!it.isImageGalleryOpen) {
                           Handler(Looper.getMainLooper()).postDelayed({
                               it.showAppOpen(0L, onShowAdCompletedAction = {})
                           }, 1000L)

                       }else {}
                   } else if (it !is SplashView && it !is PremiumPurchaseView && it !is OnboardingScreen) {
                       Log.e("AppOpen", "Showing App Open")
                       Handler(Looper.getMainLooper()).postDelayed({
                           it.showAppOpen(0L, onShowAdCompletedAction = {})
                       }, 1000L)

                   } else {
                       Log.e(
                           "AppOpen",
                           "cannot Show App Open ${mCurrentActivity?.javaClass?.simpleName}"
                       )
                   }*/
            }

        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> this.onBackgroundEntered()
            Lifecycle.Event.ON_START -> this.onForegroundEntered()
            else -> {}
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        if (!appOpen.isShowingAppOpen()) {
            mCurrentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}