

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
	activity.checkAndShowInterstitial(
                true,
                "InterstitalAdId",
                true,
                0, // timein millis to show loader
                onShowAdCompletedAction = {
                 // your logic after ad is dismissed
                },
                onInterstitialFailed = {
                   // your logic if ad failed to show
                })

# Banner Ads
## Inline Banner
### Add this xml code
	<androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/large"
        android:visibility="gone"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

        <include
            android:id="@+id/inline_banner_layout"
            layout="@layout/inline_banner" />
    </androidx.constraintlayout.widget.ConstraintLayout>
### Call the inline banner
	  activity.collapsibleBanner("BannerId")