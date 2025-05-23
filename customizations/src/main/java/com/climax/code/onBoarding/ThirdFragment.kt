package com.climax.code.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.climax.code.databinding.FragmentThirdBinding
import com.climax.code.utils.ConstantsCustomizations
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ThirdFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
private lateinit var binding : FragmentThirdBinding
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
        binding = FragmentThirdBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (onBoardingItemsList.size>=3){
            binding.tutImg.setImageResource(onBoardingItemsList[2].imageResId)
            binding.tutHeaderName.setText(onBoardingItemsList[2].title)
            binding.tutDes.setText(onBoardingItemsList[2].description)
        }
        binding.tutHeaderName.setTextColor(ContextCompat.getColor(requireContext(),ConstantsCustomizations.headerColorOnboarding))
        binding.tutDes.setTextColor(ContextCompat.getColor(requireContext(),ConstantsCustomizations.desColorOnboarding))
        binding.parentLayout.setBackgroundColor(ConstantsCustomizations.setonBoarding_Bg_Color)

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}