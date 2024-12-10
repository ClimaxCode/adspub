

# Ads Module

	maven { url 'https://jitpack.io' }


	  implementation 'com.github.ClimaxCode.adspub:adspub:latest_version'

### To show Black background on AppOpen Ad
### Add this to Manifest
 	<activity
            android:name="com.google.android.gms.ads.AdActivity"
            android:theme="@style/AdTheme"
            tools:replace="android:theme" />
### Add this to theme style
	<style name="AdTheme">
        <item name="android:background">@android:color/transparent</item>
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:windowExitAnimation">@null</item>
        <item name="android:windowEnterAnimation">@null</item>
    </style>


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
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/full%20native.jpg?raw=true)
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
	activity.callNativeAd("Native Id","large",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)
  
## Native Type 1
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/native%201.png?raw=true)
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
	activity.callNativeAd("Native Id","native1",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)


## Native Type 2
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/native%202.png?raw=true)
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
            layout="@layout/native2_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	activity.callNativeAd("Native Id","native2",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)



## Native Type 3
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/native%203.png?raw=true)
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
            layout="@layout/native3_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	activity.callNativeAd("Native Id","native3",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)




## Native Type 4
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/native%204.png?raw=true)
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
            layout="@layout/native4_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	activity.callNativeAd("Native Id","native4",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)



## Native Type 5
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/native%205.png?raw=true)
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
            layout="@layout/native5_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	activity.callNativeAd("Native Id","native5",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)



## Native Type 6
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/native%206.png?raw=true)
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
            layout="@layout/native6_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	activity.callNativeAd("Native Id","native6 ",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)


## Small Native
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/small%20native.png?raw=true)
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
             layout="@layout/small_native_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

### Call the Native Ad
	 activity.callNativeAd("Native Id","small",Preload Ad (Boolean Value),Load New Ad (Boolean Value),{actionLoaded},{actionFailed},{tryToShowAgain},Pass Button Color)



# Exit Dialogs
## Exit Dialog Style 1
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/exit1.png?raw=true)
### Call the code 
	  activity.exit1("Native ID",Ads Button Color,Exit Button Color) {
	// on exit button click
            }

## Exit Dialog Style 2
![alt text](https://github.com/ClimaxCode/adspub/blob/main/screenshots/exit2.png?raw=true)
### Call the code 
	   activity.exit2("Native ID",Ads Button Color) {
	// on exit button click
            }


# Onboarding Screen
### Step to add on Boarding Screen
### 1. Create onBoarding Activity
### 2. Add the Below Code in onCreate 
 	Constants.fullNativeButtonColor = ContextCompat.getColor(this,R.color.AdsButton)
	  com.climax.ads.adsclas.Constants.onBoardingFullScreenNativeId =
           "Full Native ID" //only add if you want to show full native 
        setonBoarding_Bg_Color = Color for background
        com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList.add(
            com.climax.code.onBoarding.OnboardingItem(
                Title,
                Description,
                Image
            )
        ) // add upto 5 Items
        
        setupViewPager()

### 3.create setupViewPager Function
	  private var showFullNative: Boolean = false
 	private fun setupViewPager() {
  	showFullNative = false
       
	com.climax.code.utils.ConstantsCustomizations.onBoardingFullNativeAtIndex= 2

        val adapter = OnboardingFragmentStateAdapter(this, true)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        binding.dotsIndicator.selectedDotColor = getColor(com.climax.code.R.color.main_color)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
               
                 if (showFullNative && position == onBoardingFullNativeAtIndex) {
                    hideBottomViews() //logic to hide add Screen data i.e Appbar,Button everything
                } else {
                    showBottomViews() //logic to show add Screen data i.e Appbar,Button everything
                }


            }
        })
    }