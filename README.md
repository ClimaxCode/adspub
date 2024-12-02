

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
