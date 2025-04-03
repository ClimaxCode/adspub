package com.climax.code

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.climax.ads.adsclas.callNativeAd
import com.climax.ads.adsclas.hide
import com.climax.ads.adsclas.hideNavigationBar
import com.climax.ads.adsclas.show
import com.climax.code.databinding.ActivityOnBoardingBinding
import com.climax.code.onBoarding.OnboardingFragmentStateAdapter
import com.climax.code.utils.ConstantsCustomizations.onBoardingFullNativeAtIndex
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList

class OnBoardingActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferences: SharedPreferences
    private var showFullNative: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.viewPager.currentItem = 0
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)

        supportActionBar?.hide()

        setupViewPager()

        binding.next.setOnClickListener {
            val maxItem =
                if (showFullNative) onBoardingItemsList.size + 1 else onBoardingItemsList.size
            if (binding.viewPager.currentItem + 1 < maxItem) {
                binding.viewPager.currentItem += 1
            } else {
                sharedPreferences.edit().putBoolean("onboarding_shown", true).apply()
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }


        binding.btnSkip.setOnClickListener {
            binding.viewPager.currentItem = 3
        }

        showNativeAd()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideNavigationBar()
        }
    }

    private fun showNativeAd() {

        callNativeAd(
            "ca-app-pub-3940256099942544/1044960115",
            "native5",
            true,
            false,
            {},
            {},
            {},R.color.red,R.color.white,R.color.main_color)

    }


    private fun setupViewPager() {
        showFullNative = true
        onBoardingFullNativeAtIndex = 3
        val adapter = OnboardingFragmentStateAdapter(this, showFullNative)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        binding.dotsIndicator.selectedDotColor = getColor(R.color.main_color)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 3) binding.btnSkip.visibility =
                    View.INVISIBLE else binding.btnSkip.visibility = View.VISIBLE


                if (showFullNative && position == onBoardingFullNativeAtIndex) {
                    hideBottomViews()
                } else {
                    showBottomViews()
                }
            }
        })
    }


    fun hideBottomViews() {
        binding.swapingLayout.hide()
        binding.onboardingAdsNativeLoadShow.hide()
        binding.btnSkip.hide()
        binding.dotsIndicator.hide()
    }

    fun showBottomViews() {
        binding.swapingLayout.show()
        binding.dotsIndicator.show()
        binding.onboardingAdsNativeLoadShow.show()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}