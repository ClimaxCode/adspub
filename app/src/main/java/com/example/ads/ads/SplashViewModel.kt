package com.example.ads.ads

import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {

    var isAppOpenAdLoaded=false
    var isBannerAdLoaded=false
    var isBannerDisplayTimeCompleted=false
    var isAppPaused=false
    var isScreenNavigationCalled=false
}