

# Ads Module

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

## Adaptive Banner
### Add this xml code
	   <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/collap"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

        <include
            android:id="@+id/small_banner_layout"
            layout="@layout/banner_layout" />
    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Adaptive banner
	   this.adaptiveBanner("BannerId")



## Collapsible Banner
### Add this xml code
	   <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/collap"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

        <include
            android:id="@+id/small_banner_layout"
            layout="@layout/banner_layout" />
    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Adaptive banner
	    this.collapsibleBanner("BannerId")


# Native Ads
## Full Native
### Add this code to XML
	    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <include
            android:id="@+id/layout1"
            layout="@layout/full_screen_native_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	callNativeAd("Native Id","large",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)
  
## Native Type 1
### Add this code to XML
	    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <include
            android:id="@+id/layout1"
            layout="@layout/native1_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	callNativeAd("Native Id","native1",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)
