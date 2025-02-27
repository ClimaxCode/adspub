package com.climax.ads.adsclas

import android.app.Activity
import android.content.res.ColorStateList
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.climax.ads.R
import com.climax.ads.databinding.ExitNative1Binding
import com.climax.ads.databinding.ExitNative2Binding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Activity?.exit1(adId: String, adButtonColor: Int,buttonTextColor:Int,exitButtonColor:Int,bgColor:Int, exit: () -> Unit) {
    var dialogBinding = ExitNative1Binding.inflate(this!!.layoutInflater)
    val exitDialog: AlertDialog = MaterialAlertDialogBuilder(
        this,
        R.style.MyRounded_MaterialComponents_MaterialAlertDialog
    )
        .setView(dialogBinding.root)
        .show()
    exitDialog.window?.setDimAmount(0.7f)
    dialogBinding.exitButton.setOnClickListener {
        exitDialog.dismiss()
        exit()

    }
    dialogBinding.cancelButton.setOnClickListener {
        exitDialog.dismiss()
    }
    showLargeNative(adId,"exit_native1",
        R.layout.exit_native1_adcontent,
        dialogBinding.layout.adRoot,
        dialogBinding.layout.adContainere1,
        dialogBinding.layout.shimmerExit1,
        false,
        true,
        {

        },
        {

        },
        {

        },
        adButtonColor,buttonTextColor,bgColor
    )
    val tintColor = ContextCompat.getColor(this,exitButtonColor)

// Apply the background tint
    dialogBinding.cancelButton.backgroundTintList = ColorStateList.valueOf(tintColor)

}

fun Activity?.exit2(adId: String, adButtonColor: Int,buttonTextColor:Int,bgColor:Int, exit: () -> Unit) {
    var dialogBinding = ExitNative2Binding.inflate(this!!.layoutInflater)
    val exitDialog = BottomSheetDialog(
        this
    )

    exitDialog.setContentView(dialogBinding.root)

    dialogBinding.exitButton.setOnClickListener {
        exitDialog.dismiss()
        exit()

    }

    showLargeNative(adId,"exit_native1",
        R.layout.exit_native2_adcontent,
        dialogBinding.layout.adRoot,
        dialogBinding.layout.adContainere2,
        dialogBinding.layout.shimmerExit2,
        false,
        true,
        {

        },
        {

        },
        {

        },
        adButtonColor,buttonTextColor,bgColor
    )

//    this.callNativeAd(adId, "exit2", false, true,
//        {
//            Log.d("adss", "onCreate: loaded")
//        }, {
//            Log.d("adss", "onCreate: failes")
//        }, {
//
//        })


    exitDialog.show()
}
