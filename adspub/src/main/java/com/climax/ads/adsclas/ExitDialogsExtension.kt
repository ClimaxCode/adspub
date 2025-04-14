package com.climax.ads.adsclas

import android.app.Activity
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.climax.ads.R
import com.climax.ads.databinding.ExitNative1Binding
import com.climax.ads.databinding.ExitNative2Binding
import com.climax.ads.databinding.ExitNative3Binding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Activity?.exit2(adId: String,preload:Boolean, adButtonColor: Int,buttonTextColor:Int,exitButtonColor:Int,bgColor:Int,isdarkMode:Boolean,dialogbgColor:Int, exit: () -> Unit) {
    var dialogBinding = ExitNative1Binding.inflate(this!!.layoutInflater)
    val exitDialog: AlertDialog = MaterialAlertDialogBuilder(
        this,
        R.style.MyRounded_MaterialComponents_MaterialAlertDialog
    )
        .setView(dialogBinding.root)
        .show()
    exitDialog.window?.setDimAmount(0.7f)

    dialogBinding.container2.visibility = View.VISIBLE


    if (isdarkMode) {

        dialogBinding.title.setTextColor(getColor(R.color.txtcolorprimarydark))
        dialogBinding.desc.setTextColor(getColor(R.color.txtcolorsecondaydark))
        dialogBinding.exitButton.setTextColor(getColor(R.color.txtcolorsecondaydark))

    }else{
        dialogBinding.title.setTextColor(getColor(R.color.txtcolorprimaryLight))
        dialogBinding.desc.setTextColor(getColor(R.color.txtcolorsecondayLight))
        dialogBinding.exitButton.setTextColor(getColor(R.color.txtcolorsecondayLight))

    }
    Log.d("colorcode", "exit1: ")
    dialogBinding.bgcolor.setBackgroundColor(ContextCompat.getColor(this,dialogbgColor))
    dialogBinding.exitButton.setOnClickListener {
        exitDialog.dismiss()
        exit()

    }
    dialogBinding.cancelButton.setOnClickListener {
        exitDialog.dismiss()
    }
    showLargeNative(adId,"native2",
        R.layout.exit_native1_adcontent,
        dialogBinding.layout2.adRoot,
        dialogBinding.layout2.adContainer2,
        dialogBinding.layout2.shimmer2,
        preload,
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

fun Activity?.exit1(adId: String,nativeAdtype:String,preload:Boolean, adButtonColor: Int,buttonTextColor:Int,bgColor:Int, exit: () -> Unit) {
    var dialogBinding = ExitNative2Binding.inflate(this!!.layoutInflater)
    val exitDialog = BottomSheetDialog(
        this
    )

    exitDialog.setContentView(dialogBinding.root)

    dialogBinding.exitButton.setOnClickListener {
        exitDialog.dismiss()
        exit()

    }


    var type: Int = 0
    var frameLayout: FrameLayout? = null
    var shimmer: ShimmerFrameLayout? = null
    Log.d("Ads", "callNativeAd: $nativeAdtype")
    when (nativeAdtype) {
//        "large" -> {
//            type = R.layout.full_native
//            frameLayout = this?.findViewById(R.id.adContainerFull)!!
//            shimmer = this.findViewById(R.id.shimmmmer)!!
//        }

        "native1" -> {
            dialogBinding.container1.visibility = View.VISIBLE
            type = R.layout.native1
            frameLayout = dialogBinding.layout.adContainer1
            shimmer = dialogBinding.layout.shimmer1
        }

        "native2" -> {
            dialogBinding.container2.visibility = View.VISIBLE
            type = R.layout.native2
            frameLayout = dialogBinding.layout2.adContainer2
            shimmer = dialogBinding.layout2.shimmer2
        }

        "native3" -> {
            dialogBinding.container3.visibility = View.VISIBLE
            type = R.layout.native3
            frameLayout = dialogBinding.layout3.adContainer3
            shimmer = dialogBinding.layout3.shimmer3
        }

        "native4" -> {
            dialogBinding.container4.visibility = View.VISIBLE
            type = R.layout.native4
            frameLayout = dialogBinding.layout4.adContainer4
            shimmer = dialogBinding.layout4.shimmer4
        }

        "native5" -> {
            dialogBinding.container5.visibility = View.VISIBLE
            type = R.layout.native5
            frameLayout = dialogBinding.layout5.adContainer5
            shimmer = dialogBinding.layout5.shimmer5
        }

        "native6" -> {
            dialogBinding.container6.visibility = View.VISIBLE
            type = R.layout.native6
            frameLayout = dialogBinding.layout6.adContainer6
            shimmer = dialogBinding.layout6.shimmer6
        }
        "native7" -> {
            dialogBinding.container7.visibility = View.VISIBLE
            type = R.layout.native7
            frameLayout = dialogBinding.layout7.adContainer7
            shimmer = dialogBinding.layout7.shimmer7
        }
        "native8" -> {
            dialogBinding.container8.visibility = View.VISIBLE
            type = R.layout.native8
            frameLayout = dialogBinding.layout8.adContainer8
            shimmer = dialogBinding.layout8.shimmer8
        }

        "small" -> {
            dialogBinding.container9.visibility = View.VISIBLE
            type = R.layout.small_native
            frameLayout = dialogBinding.layout9.adContainers
            shimmer = dialogBinding.layout9.shimmers
        }

        else -> {
            return
        }

    }
    Log.d("Ads", "callNativeAd: $type")
//    showLargeNative(
//        nativeAdId,
//        nativeAdtype,
//        type,
//        this.findViewById(R.id.ad_root),
//        frameLayout,
//        shimmer,
//        preLoad,
//        loadNewAd,
//        actionLoaded,
//        actionFailed,
//        tryToShowAgain,
//        actionButtonColor,
//        actionButtonTextColor,
//        bgColor
//    )

    showLargeNative(adId,nativeAdtype,
        type,
        dialogBinding.layout2.adRoot,
        frameLayout,
        shimmer,
        preload,
        true,
        {

        },
        {

        },
        {

        },
        adButtonColor,buttonTextColor,bgColor
    )

    exitDialog.show()
}
fun Activity?.exit3(adId: String,preload:Boolean, adButtonColor: Int,buttonTextColor:Int,bgColor:Int,isdarkMode:Boolean,dialogbgColor:Int, exit: () -> Unit) {
    var dialogBinding = ExitNative3Binding.inflate(this!!.layoutInflater)
    val exitDialog: AlertDialog = MaterialAlertDialogBuilder(
        this,
        R.style.MyRounded_MaterialComponents_MaterialAlertDialog2
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
    if (isdarkMode) {
      dialogBinding.exitButton.setTextColor(getColor(R.color.txtcolorprimarydark))
        dialogBinding.cancelButton.setTextColor(getColor(R.color.txtcolorprimarydark))
        dialogBinding.title.setTextColor(getColor(R.color.txtcolorprimarydark))
        dialogBinding.desc.setTextColor(getColor(R.color.txtcolorsecondaydark))
    }else{
        dialogBinding.exitButton.setTextColor(getColor(R.color.txtcolorprimaryLight))
        dialogBinding.cancelButton.setTextColor(getColor(R.color.txtcolorprimaryLight))
        dialogBinding.title.setTextColor(getColor(R.color.txtcolorprimaryLight))
        dialogBinding.desc.setTextColor(getColor(R.color.txtcolorsecondayLight))

    }
    dialogBinding.dialogBg.setBackgroundColor(ContextCompat.getColor(this,dialogbgColor))
    showLargeNative(adId,"exit_native1",
        R.layout.native7,
        dialogBinding.layout.adRoot,
        dialogBinding.layout.adContainer7,
        dialogBinding.layout.shimmer7,
        preload,
        true,
        {

        },
        {

        },
        {

        },
        adButtonColor,buttonTextColor,bgColor
    )
}

//fun Activity?.adstype(type:String,adId: String){
//    when(type){
//        "native1" ->{
//            showLargeNative(adId,"exit_native1",
//                R.layout.native2,
//                dialogBinding.layout2.adRoot,
//                dialogBinding.layout2.adContainer2,
//                dialogBinding.layout2.shimmer2,
//                preload,
//                true,
//                {
//
//                },
//                {
//
//                },
//                {
//
//                },
//                adButtonColor,buttonTextColor,bgColor
//            )
//        }
//
//    }
//}