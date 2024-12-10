package com.climax.code

import android.app.Activity
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


public abstract class BaseDialog constructor() : BottomSheetDialogFragment() {




    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        (context as (Activity)).windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        dialog?.window?.setLayout((width * .85).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
    }


}