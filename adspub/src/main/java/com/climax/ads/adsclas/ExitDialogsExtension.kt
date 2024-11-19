package com.climax.ads.adsclas

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.climax.ads.R
import com.climax.ads.databinding.ExitNative1Binding
import com.climax.ads.databinding.ExitNative2Binding
import com.google.android.material.dialog.MaterialAlertDialogBuilder




fun  Activity?.exit1(exit:()->Unit) {
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
}

fun  Activity?.exit2(exit:()->Unit) {
    var dialogBinding = ExitNative2Binding.inflate(this!!.layoutInflater)
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
}
