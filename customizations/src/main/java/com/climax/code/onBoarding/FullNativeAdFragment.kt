package com.climax.code.onBoarding
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.callNativeAd
import com.climax.ads.adsclas.showLargeNative
import com.climax.ads.adsclas.showShowFullNative
import com.climax.code.R
import com.climax.code.databinding.FragmentFullNativeAdBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FullNativeAdFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentFullNativeAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullNativeAdBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFullNativeAd()
    }
    private fun showFullNativeAd() {
        Log.d("Loaddede", "showFullNativeAd: ${Constants.onBoardingFullScreenNativeId}")
        activity?.let {

            it.showLargeNative(
                Constants.onBoardingFullScreenNativeId,
                com.climax.ads.R.layout.full_native,
                binding.adRoots,
                binding.adContainers,
                binding.shimmmmers,
                false,false,{},
                {}, {}, com.climax.ads.R.color.Adscolor)
       }

//        activity?.let {
//            requireActivity().callNativeAd(
//                "ca-app-pub-3940256099942544/1044960115",
//                "large",
//                false,
//                false,
//                {},
//                {},
//                {})
//        }

    }


//    override fun onResume() {
//        super.onResume()
//        activity?.let {
//            if(it is OnboardingActivity){
//                it.hideBottomViews()
//            }
//        }
//    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FullNativeAdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}