package com.climax.code

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.climax.ads.adsclas.helpers.adaptiveBanner
import com.climax.ads.adsclas.helpers.collapsibleBanner
import com.climax.ads.adsclas.helpers.inlineBanner
import com.climax.code.databinding.ActivityBannerBinding

class BannerActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityBannerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.getStringExtra("style").equals("collap")) {
           binding.collap.visibility  = View.VISIBLE
           this.collapsibleBanner("ca-app-pub-3940256099942544/9214589741",binding.smallBannerLayout.bannerAdLayout,binding.smallBannerLayout.shimmerViewContainer)

        } else if (intent.getStringExtra("style").equals("inline")) {
            binding.large.visibility  = View.VISIBLE
            this.inlineBanner("ca-app-pub-3940256099942544/9214589741",binding.inlineBannerLayout.bannerAdLayout,binding.inlineBannerLayout.shimmerViewContainer)

        }
        else if (intent.getStringExtra("style").equals("adaptive")) {
            binding.collap.visibility  = View.VISIBLE
            this.adaptiveBanner("ca-app-pub-3940256099942544/9214589741",binding.smallBannerLayout.bannerAdLayout,binding.smallBannerLayout.shimmerViewContainer)

        }

    }
}