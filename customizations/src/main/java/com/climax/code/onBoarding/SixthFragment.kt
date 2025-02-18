package com.climax.code.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.climax.code.R
import com.climax.code.databinding.FragmentSixthBinding
import com.climax.code.databinding.FragmentThirdBinding
import com.climax.code.utils.ConstantsCustomizations
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList


class SixthFragment : Fragment() {
    private lateinit var binding : FragmentSixthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSixthBinding.inflate(layoutInflater,container,false)
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
        binding.parentLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),ConstantsCustomizations.setonBoarding_Bg_Color))

    }
}