package com.climax.code

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.climax.ads.adsclas.checkAndShowInterstitial
import com.climax.ads.adsclas.loadPreInterstitial
import com.climax.ads.adsclas.showLargeNative
import com.climax.ads.adsclas.showShowFullNative
import com.climax.code.databinding.ActivityNativeAdsCallBinding

class NativeAdsCallActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNativeAdsCallBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.getStringExtra("style").equals("one")){
            binding.one.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.full_native,
                binding.layout.adRoot,binding.layout.adContainer,binding.layout.shimmerViewContainer,false,true,{

                },{

                },{

                })
        } else   if (intent.getStringExtra("style").equals("two")){
            binding.two.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.native1,
                binding.layout1.adRoot,binding.layout1.adContainer,binding.layout1.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }
        else   if (intent.getStringExtra("style").equals("three")){
            binding.three.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.native2,
                binding.layout2.adRoot,binding.layout2.adContainer,binding.layout2.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }
        else   if (intent.getStringExtra("style").equals("four")){
            binding.four.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.native3,
                binding.layout3.adRoot,binding.layout3.adContainer,binding.layout3.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }
        else   if (intent.getStringExtra("style").equals("five")){
            binding.five.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.native4,
                binding.layout4.adRoot,binding.layout4.adContainer,binding.layout4.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }
        else   if (intent.getStringExtra("style").equals("six")){
            binding.six.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.native5,
                binding.layout5.adRoot,binding.layout5.adContainer,binding.layout5.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }
        else   if (intent.getStringExtra("style").equals("seven")){
            binding.seven.visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.native6,
                binding.layout6.adRoot,binding.layout6.adContainer,binding.layout6.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }
        else   if (intent.getStringExtra("style").equals("eight")){
            binding.eight .visibility = View.VISIBLE
            showLargeNative("ca-app-pub-3940256099942544/1044960115", com.climax.ads.R.layout.small_native,
                binding.layout7.adRoot,binding.layout7.adContainer,binding.layout7.shimmerViewContainer,false,true,{

                },{

                },{

                })
        }





    }
}