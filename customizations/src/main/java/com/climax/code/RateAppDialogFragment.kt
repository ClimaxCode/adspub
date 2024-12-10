package com.climax.code

import android.content.Context
import android.content.DialogInterface
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.climax.ads.R
import com.climax.ads.adsclas.AdaptiveBannerAd
import com.climax.ads.adsclas.helpers.adaptiveBanner
import com.climax.ads.adsclas.invisible
import com.climax.ads.adsclas.isNetworkAvailable
import com.climax.ads.adsclas.setOnSingleClickListener
import com.climax.ads.adsclas.show
import com.climax.code.databinding.RateAppLayoutBinding
import com.climax.code.utils.ConstantsCustomizations
import com.climax.code.utils.OnRateAppExitClickListeners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Job

class RateAppDialogFragment : BottomSheetDialogFragment() {
    private var onActionExit: (() -> Unit)? = null
    private var onActionFeedback: (() -> Unit)? = null
    private var onActionRateus: (() -> Unit)? = null
    private var image: Int? = null
    private var title: String? = null
    private var exitTitle: String? = null
    private var adId: String? = null

    companion object {
        fun newInstance(
            image: Int,
            title:String,
            exitTitle:String,
            adId:String,
            dialogType: String?,
            onActionExit: (() -> Unit)? = null,
            onActionFeedback: (() -> Unit)? = null,
            onActionRateus: (() -> Unit)? = null
        ): RateAppDialogFragment {
            val rateAppDialogFragment = RateAppDialogFragment()
            val args = Bundle()
            args.putString(KEY_DIALOG_TYPE, dialogType)
            rateAppDialogFragment.arguments = args
            rateAppDialogFragment.image = image
            rateAppDialogFragment.title = title
            rateAppDialogFragment.exitTitle = exitTitle
            rateAppDialogFragment.adId = adId
            rateAppDialogFragment.onActionExit = onActionExit
            rateAppDialogFragment.onActionFeedback = onActionFeedback
            rateAppDialogFragment.onActionRateus = onActionRateus
            return rateAppDialogFragment
        }

        const val AD_DIALOG_TAG = "AdDialogFragment"
        const val KEY_DIALOG_TYPE = "reward_dialog_type"

    }

    fun triggerActionExit() {
        onActionExit?.invoke()
    }

    fun triggerActionFeedBack() {
        onActionFeedback?.invoke()
    }

    fun triggerActionRate() {
        onActionRateus?.invoke()
    }


    private var listener: OnRateAppExitClickListeners? = null

    lateinit var binding: RateAppLayoutBinding
    var adJob: Job? = null

    var dialogType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RateAppLayoutBinding.inflate(inflater, container, false)
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

        binding.txtRateEx.text = title
        binding.imgRateIcon.setImageResource(image!!)
        binding.btnExit.text = exitTitle

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

        if (adId =="" || !isNetworkAvailable()){
            binding.nativead.visibility =View.GONE
        }else{
            binding.nativead.visibility =View.VISIBLE
            var admobBannerAdManager = AdaptiveBannerAd(requireContext())
            admobBannerAdManager.loadAdaptiveBanner(
                adId!!,
                binding.bannerAdLayout,
                binding.shimmerBanner
            )
        }


        binding.btnRateus.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), ConstantsCustomizations.buttonColorRate));
        binding.btnRateus.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
        binding.btnFeedback.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), ConstantsCustomizations.buttonColorRate));
        binding.btnFeedback.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
        binding.btnRateus.setOnClickListener {

            dialog?.dismiss()
            listener?.onRateClick(4)
            triggerActionRate()
        }
        binding.btnFeedback.setOnClickListener {

            dialog?.dismiss()
            listener?.onRateClick(3)
            triggerActionFeedBack()
        }
        binding.btnExit.setOnClickListener {
            dialog?.dismiss()
            listener?.onLaterClick()
            triggerActionExit()
        }

        binding.star1.setOnSingleClickListener {
            enableBtnStates(1)
        }
        binding.star2.setOnSingleClickListener {
            enableBtnStates(2)
        }
        binding.star3.setOnSingleClickListener {
            enableBtnStates(3)
        }
        binding.star4.setOnSingleClickListener {
            enableBtnStates(4)
        }
        binding.star5.setOnSingleClickListener {
            enableBtnStates(5)
        }

    }

    private fun enableBtnStates(ratingValue: Int) {
        val filledStarRes = ConstantsCustomizations.filledStarDrawable
        val emptyStarRes = ConstantsCustomizations.unfilledStarDrawable
        when (ratingValue) {
            0 -> {
                binding.star1.setImageResource(emptyStarRes)
                binding.star2.setImageResource(emptyStarRes)
                binding.star3.setImageResource(emptyStarRes)
                binding.star4.setImageResource(emptyStarRes)
                binding.star5.setImageResource(emptyStarRes)

            }

            1 -> {
                binding.star1.setImageResource(filledStarRes)
                binding.star2.setImageResource(emptyStarRes)
                binding.star3.setImageResource(emptyStarRes)
                binding.star4.setImageResource(emptyStarRes)
                binding.star5.setImageResource(emptyStarRes)
            }

            2 -> {
                binding.star1.setImageResource(filledStarRes)
                binding.star2.setImageResource(filledStarRes)
                binding.star3.setImageResource(emptyStarRes)
                binding.star4.setImageResource(emptyStarRes)
                binding.star5.setImageResource(emptyStarRes)
            }

            3 -> {
                binding.star1.setImageResource(filledStarRes)
                binding.star2.setImageResource(filledStarRes)
                binding.star3.setImageResource(filledStarRes)
                binding.star4.setImageResource(emptyStarRes)
                binding.star5.setImageResource(emptyStarRes)
            }

            4 -> {
                binding.star1.setImageResource(filledStarRes)
                binding.star2.setImageResource(filledStarRes)
                binding.star3.setImageResource(filledStarRes)
                binding.star4.setImageResource(filledStarRes)
                binding.star5.setImageResource(emptyStarRes)
            }

            5 -> {
                binding.star1.setImageResource(filledStarRes)
                binding.star2.setImageResource(filledStarRes)
                binding.star3.setImageResource(filledStarRes)
                binding.star4.setImageResource(filledStarRes)
                binding.star5.setImageResource(filledStarRes)
            }

        }
        showRateUsBtnState(ratingValue)
    }

    private fun showRateUsBtnState(ratingValue: Int) {
        when (ratingValue) {

            1, 2, 3 -> {
                binding.btnRateus.invisible()
                binding.btnFeedback.show()
            }

            4, 5 -> {
                binding.btnRateus.show()
                binding.btnFeedback.invisible()
            }
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
    fun isNetworkAvailable(): Boolean {
        this?.let {
            val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val networkCapabilities = cm?.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> true
            }
        }
        return false
    }

}