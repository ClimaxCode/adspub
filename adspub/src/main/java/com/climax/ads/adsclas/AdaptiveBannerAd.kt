package com.climax.ads.adsclas

import android.content.Context

import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.climax.ads.adsclas.Constants.applovinBannerAd
import com.climax.ads.adsclas.applovin.loadApplovinBannerCompose
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdaptiveBannerAd(var context: Context) {
    private lateinit var adView: AdView
    private var TAG = "AdaptiveBanner"

    fun loadAdaptiveBanner(
        bannerId: String,
        adLayout: FrameLayout,
        loadingText: ShimmerFrameLayout,
        onShowAdCompletedAction: ((Boolean) -> Unit)? = null
    ) {
        if (!Constants.isPurchased() && context.isNetworkAvailable()) {
            adView = AdView(context)

            val adLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

            adView.layoutParams = adLayoutParams

            adLayout.addView(adView)

            adView.adUnitId = bannerId


            val display = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            display.defaultDisplay.getMetrics(outMetrics)


            val density = outMetrics.density

            var adWidthPixels = adLayout.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

            adView.setAdSize(adSize)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

            adView.adListener = object : AdListener() {
                override fun onAdClicked() {
                    Log.d(TAG, "onAdClicked: ")
                }

                override fun onAdClosed() {
                    Log.d(TAG, "onAdClosed: ")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "onAdFailedToLoad: ")
                    loadingText.visibility = View.GONE
                    onShowAdCompletedAction?.invoke(false)
                    if (Constants.isApplovinEnabled) {
                        Log.e("Applovin_TAG_NEW", "Loading Applovin Banner")
                        Constants.applovinBannerAd.loadApplovinBanner(
                            context,
                            adLayout,
                            Constants.applovinBannerId,
                            loadingText
                        )
                    } else {
                        adLayout.visibility = View.GONE
                        loadingText.visibility = View.GONE
                    }
                }

                override fun onAdImpression() {
                    Log.d(TAG, "onAdImpression: ")
                }

                override fun onAdLoaded() {
                    Log.d(TAG, "onAdLoaded: ")
                    loadingText.visibility = View.GONE
                    onShowAdCompletedAction?.invoke(true)
                }

                override fun onAdOpened() {
                    Log.d(TAG, "onAdOpened: ")
                }
            }
        } else {
            onShowAdCompletedAction?.invoke(false)
            adLayout.visibility = View.GONE
            //  containerLayout.visibility = View.GONE
            loadingText.visibility = View.GONE

        }
    }


}

@Composable
fun loadAdaptiveBannerCompose(
    context: Context,
    modifier: Modifier = Modifier,
    bannerId: String,
    onAdLoaded: ((Boolean) -> Unit)? = null,
    onApplovinAdLoaded: ((Boolean) -> Unit)? = null
) {
    var isAdLoaded by remember { mutableStateOf(false) }
    var useAppLovin by remember { mutableStateOf(false) }
    var loadAppLovin by remember { mutableStateOf(Constants.isApplovinEnabled) }

    val adView = remember { AdView(context) }
    val density = LocalDensity.current.density
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = remember {
        DisplayMetrics().apply {
            windowManager.defaultDisplay.getMetrics(this)
        }
    }

    val adWidthPixels = outMetrics.widthPixels.toFloat()
    val adWidth = (adWidthPixels / density).toInt()
    val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

    LaunchedEffect(bannerId) {
        if (!Constants.isPurchased() && context.isNetworkAvailable()) {
            adView.setAdSize(adSize)
            adView.adUnitId = bannerId

            val adRequest = AdRequest.Builder().build()

            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    isAdLoaded = true
                    onAdLoaded?.invoke(true)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    isAdLoaded = false
                    onAdLoaded?.invoke(false)
                    useAppLovin = true // ✅ Trigger fallback
                }
            }

            adView.loadAd(adRequest)
        } else {
            onAdLoaded?.invoke(false)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(adSize.height.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isAdLoaded -> {
                AndroidView(
                    factory = { adView },
                    modifier = Modifier.fillMaxSize()
                )
            }

            useAppLovin -> {
                // ✅ Safely compose fallback here
                if (loadAppLovin) {
                    loadApplovinBannerCompose(
                        context,
                        modifier,
                        Constants.applovinBannerId,
                        onAdLoaded,onApplovinAdLoaded

                    )
                }else{
                    onAdLoaded?.invoke(false)
                    onApplovinAdLoaded?.invoke(false)
                }

            }

            else -> {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RectangleShape)
                )
            }
        }
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnim"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Spacer(
        modifier = modifier
            .background(brush, shape)
    )
}
