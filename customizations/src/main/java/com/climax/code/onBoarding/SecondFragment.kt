package com.climax.code.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.climax.code.databinding.FragmentSecondBinding
import com.climax.code.utils.ConstantsCustomizations
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList

private const val ARG_PARAM1a = "param1"
private const val ARG_PARAM2a = "param2"


class SecondFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1a)
            param2 = it.getString(ARG_PARAM2a)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (onBoardingItemsList.size>=2){
            binding.tutImg.setImageResource(onBoardingItemsList[1].imageResId)
            binding.tutHeaderName.setText(onBoardingItemsList[1].title)
            binding.tutDes.setText(onBoardingItemsList[1].description)
        }
        binding.parentLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),ConstantsCustomizations.setonBoarding_Bg_Color))
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1a, param1)
                    putString(ARG_PARAM2a, param2)
                }
            }
    }
}