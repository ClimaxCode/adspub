package com.climax.ads.adsclas

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.climax.ads.R
import com.climax.ads.databinding.ExitNative1Binding
import com.climax.ads.databinding.ExitNative2Binding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Activity?.exit1(adId: String, exit: () -> Unit) {
    var dialogBinding = ExitNative1Binding.inflate(this!!.layoutInflater)
    val exitDialog: AlertDialog = MaterialAlertDialogBuilder(
        this,
        R.style.MyRounded_MaterialComponents_MaterialAlertDialog
    )
        .setView(dialogBinding.root)
        .show()

    dialogBinding.exitButton.setOnClickListener {
        exitDialog.dismiss()
        exit()

    }
    dialogBinding.cancelButton.setOnClickListener {
        exitDialog.dismiss()
    }
    showLargeNative(adId, R.layout.exit_native1_adcontent,
        dialogBinding.layout.adRoot,dialogBinding.layout.adContainere1,dialogBinding.layout.shimmerExit1,false,true,{

        },{

        },{

        })

//    this.callNativeAd(adId, "exit1", false, true,
//        {
//            Log.d("adss", "onCreate: loaded")
//        }, {
//            Log.d("adss", "onCreate: failes")
//        }, {
//
//        })

}

fun Activity?.exit2(adId: String, exit: () -> Unit) {
    var dialogBinding = ExitNative2Binding.inflate(this!!.layoutInflater)
    val exitDialog = BottomSheetDialog(
        this
    )

    exitDialog.setContentView(dialogBinding.root)

    dialogBinding.exitButton.setOnClickListener {
        exitDialog.dismiss()
        exit()

    }

    showLargeNative(adId, R.layout.exit_native2_adcontent,
        dialogBinding.layout.adRoot,dialogBinding.layout.adContainere2,dialogBinding.layout.shimmerExit2,false,true,{

        },{

        },{

        })

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
