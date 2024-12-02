

# AndModuleAds

	maven { url 'https://jitpack.io' }


	  implementation 'com.github.ClimaxCode.adspub:adspub:latest_version'

## AppOpen Ads

### Create Application Class and add this code 

 private var appObserver: AppObserver? = null

      appObserver = AppObserver(
            appOpen = AppOpen(),
            AppopenID,
            "Activity" // Name of activity on which you don't want to show AppOpen i.e. Splash Activity
        )
        appObserver?.let {
            ProcessLifecycleOwner.get().lifecycle.addObserver(it)
            this.registerActivityLifecycleCallbacks(it)
        }

## Interstital Ads

### Preload Interstistal
    activity.loadPreInterstitial("InterstitalAdId") // only In preload scenrio 

### Show Interstistal

   this.checkAndShowInterstitial(
                true,
                "InterstitalAdId",
                true,
                0, // timein millis to show loader
                onShowAdCompletedAction = {
                    var intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
                    intent.putExtra("failed", false)
                    startActivity(intent)
                },
                onInterstitialFailed = {
                    var intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
                    intent.putExtra("failed", true)
                    startActivity(intent)
                })