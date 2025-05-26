package com.climax.code

import android.content.DialogInterface
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.climax.ads.adsclas.setOnSingleClickListener
import com.climax.code.databinding.FragmentRateUsDialog2Binding
import com.climax.code.utils.ConstantsCustomizations
import com.climax.code.utils.OnRateAppExitClickListeners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Job

class RateUsDialog2 : BottomSheetDialogFragment() {
    private var onActionExit: (() -> Unit)? = null

    private var onActionRateus: (() -> Unit)? = null
    private var title: String? = null
    private var exitTitle: String? = null


    companion object {
        fun newInstance(
            title: String,
            exitTitle: String,
            onActionExit: (() -> Unit)? = null,
            onActionRateus: (() -> Unit)? = null
        ): RateUsDialog2 {
            val rateAppDialogFragment = RateUsDialog2()
            rateAppDialogFragment.title = title
            rateAppDialogFragment.exitTitle = exitTitle
            rateAppDialogFragment.onActionExit = onActionExit
            rateAppDialogFragment.onActionRateus = onActionRateus
            return rateAppDialogFragment
        }

        const val AD_DIALOG_TAG = "AdDialogFragment"
        const val KEY_DIALOG_TYPE = "reward_dialog_type"

    }

    fun triggerActionExit() {
        onActionExit?.invoke()
    }

//    fun triggerActionFeedBack() {
//        onActionFeedback?.invoke()
//    }

    fun triggerActionRate() {
        onActionRateus?.invoke()
    }


    private var listener: OnRateAppExitClickListeners? = null

    lateinit var binding: FragmentRateUsDialog2Binding
    var adJob: Job? = null

    var dialogType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRateUsDialog2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogType = arguments?.getString(KEY_DIALOG_TYPE)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableBtnStates(4)
        setupClickListeners()


    }

    fun setAdDialogInteractionListener(listener: OnRateAppExitClickListeners) {
        this.listener = listener
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(AD_DIALOG_TAG, "onCancel: Calling onCancelAd().")

        //listener?.onCancelAd(dialogType = dialogType)
    }

    private fun setupClickListeners() {

        binding.bgRate2.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.bgColorRate
            )
        )
        binding.imgStar1.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)



        binding.done.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.buttonColorRate
            )
        )
        binding.done.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)

        binding.imgStar1.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.buttonColorRate
            )
        )
        binding.imgStar1.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)

        binding.imgStar2.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.buttonColorRate
            )
        )
        binding.imgStar2.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)

        binding.imgStar3.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.buttonColorRate
            )
        )
        binding.imgStar3.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)

        binding.imgStar4.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.buttonColorRate
            )
        )
        binding.imgStar4.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)

        binding.imgStar5.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireContext(),
                ConstantsCustomizations.buttonColorRate
            )
        )
        binding.imgStar5.setBackgroundTintMode(PorterDuff.Mode.SRC_IN)

        binding.cancel.text = exitTitle

        binding.done.setOnClickListener {

            dialog?.dismiss()
            listener?.onRateClick(4)
            triggerActionRate()
        }

        binding.cancel.setOnClickListener {
            dialog?.dismiss()
            listener?.onLaterClick()
            triggerActionExit()
        }

        binding.imgStar1.setOnSingleClickListener {
            enableBtnStates(1)
        }
        binding.imgStar2.setOnSingleClickListener {
            enableBtnStates(2)
        }
        binding.imgStar3.setOnSingleClickListener {
            enableBtnStates(3)
        }
        binding.imgStar4.setOnSingleClickListener {
            enableBtnStates(4)
        }
        binding.imgStar5.setOnSingleClickListener {
            enableBtnStates(5)
        }

    }

    private fun enableBtnStates(ratingValue: Int) {
        val filledStarRes = ConstantsCustomizations.filledStarDrawable
        val emptyStarRes = ConstantsCustomizations.unfilledStarDrawable
        when (ratingValue) {
            0 -> {
                binding.imgStar1.setImageResource(emptyStarRes)
                binding.imgStar2.setImageResource(emptyStarRes)
                binding.imgStar3.setImageResource(emptyStarRes)
                binding.imgStar4.setImageResource(emptyStarRes)
                binding.imgStar5.setImageResource(emptyStarRes)


            }

            1 -> {
                binding.imgStar1.setImageResource(filledStarRes)
                binding.imgStar2.setImageResource(emptyStarRes)
                binding.imgStar3.setImageResource(emptyStarRes)
                binding.imgStar4.setImageResource(emptyStarRes)
                binding.imgStar5.setImageResource(emptyStarRes)
            }

            2 -> {
                binding.imgStar1.setImageResource(filledStarRes)
                binding.imgStar2.setImageResource(filledStarRes)
                binding.imgStar3.setImageResource(emptyStarRes)
                binding.imgStar4.setImageResource(emptyStarRes)
                binding.imgStar5.setImageResource(emptyStarRes)
            }

            3 -> {
                binding.imgStar1.setImageResource(filledStarRes)
                binding.imgStar2.setImageResource(filledStarRes)
                binding.imgStar3.setImageResource(filledStarRes)
                binding.imgStar4.setImageResource(emptyStarRes)
                binding.imgStar5.setImageResource(emptyStarRes)
            }

            4 -> {
                binding.imgStar1.setImageResource(filledStarRes)
                binding.imgStar2.setImageResource(filledStarRes)
                binding.imgStar3.setImageResource(filledStarRes)
                binding.imgStar4.setImageResource(filledStarRes)
                binding.imgStar5.setImageResource(emptyStarRes)
            }

            5 -> {
                binding.imgStar1.setImageResource(filledStarRes)
                binding.imgStar2.setImageResource(filledStarRes)
                binding.imgStar3.setImageResource(filledStarRes)
                binding.imgStar4.setImageResource(filledStarRes)
                binding.imgStar5.setImageResource(filledStarRes)
            }

        }
        showRateUsBtnState(ratingValue)
    }

    private fun showRateUsBtnState(ratingValue: Int) {
        when (ratingValue) {

//            1, 2, 3 -> {
//                binding.btnRateus.invisible()
//                binding.btnFeedback.show()
//            }
//
//            4, 5 -> {
//                binding.btnRateus.show()
//                binding.btnFeedback.invisible()
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(AD_DIALOG_TAG, "onCancel: Calling onCancelAd().")

        //listener?.onCancelAd(dialogType = dialogType)
    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
        adJob?.cancel()
        //  listener?.onCancelAd(dialogType = dialogType)
    }


}