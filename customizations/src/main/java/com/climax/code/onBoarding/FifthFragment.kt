package com.climax.code.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.climax.code.R
import com.climax.code.databinding.FragmentFifthBinding
import com.climax.code.databinding.FragmentSecondBinding
import com.climax.code.databinding.FragmentThirdBinding
import com.climax.code.utils.ConstantsCustomizations
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList


private const val ARG_PARAM1b = "param1"
private const val ARG_PARAM2b = "param2"

class FifthFragment : Fragment() {

    private lateinit var binding : FragmentFifthBinding
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1b)
            param2 = it.getString(ARG_PARAM2b)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFifthBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (onBoardingItemsList.size>=5){
            binding.tutImg.setImageResource(onBoardingItemsList[4].imageResId)
            binding.tutHeaderName.setText(onBoardingItemsList[4].title)
            binding.tutDes.setText(onBoardingItemsList[4].description)
        }
        binding.parentLayout.setBackgroundColor(ConstantsCustomizations.setonBoarding_Bg_Color)

    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1b, param1)
                    putString(ARG_PARAM2b, param2)
                }
            }
    }
}