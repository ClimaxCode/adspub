package com.climax.code.applanguages

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.climax.ads.adsclas.Constants
import com.climax.ads.adsclas.Constants.viewLineColor
import com.climax.code.R
import com.climax.code.databinding.AppLanguagesBinding

class AppLanguagesAdapter(
    private val appLanguageList: List<AppLanguagesModel>,
    private val listener: OnAppLangItemClickListener
) :
    RecyclerView.Adapter<AppLanguagesAdapter.MyViewHolder>() {

    private var mlistener: OnAppLangItemClickListener = listener
    private var isApplied: PrefStorage? = null
    var context: Context? = null
    private var isSelectedItem = 0
    private var isListClick = false

    init {
        mlistener = listener
    }

    interface OnAppLangItemClickListener {
        fun onLangItemClick(position: Int, item: AppLanguagesModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding =
            AppLanguagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, mlistener)
    }

    @SuppressLint("LogNotTimber")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        isApplied = context?.let { PrefStorage(it) }
        val currentItem = appLanguageList[holder.adapterPosition]

        val isDarkMode =
            when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> true
                else -> false
            }

        if (isDarkMode) {
            context?.let {
                holder.binding.langName.setTextColor(ContextCompat.getColor(it, R.color.white))
                holder.binding.currentLangName.setTextColor(
                    ContextCompat.getColor(
                        it,
                        R.color.gray_dark
                    )
                )
            }
        } else {
            context?.let {
                holder.binding.langName.setTextColor(
                    ContextCompat.getColor(
                        it,
                        Constants.countryNameTextColor
                    )
                )
                holder.binding.currentLangName.setTextColor(
                    ContextCompat.getColor(
                        it,
                        Constants.languageNameTextColor
                    )
                )
            }
        }

        context?.let {

            holder.binding.viewLine.setBackgroundColor(ContextCompat.getColor(it, viewLineColor))
        }
        holder.binding.imgLangFlag.setImageResource(currentItem.imageId)
        holder.binding.langName.text = currentItem.countryName
        holder.binding.currentLangName.text = currentItem.langName
        Log.e("showLogTag", "Language is ${isApplied?.intAppLangApplied}")
        holder.binding.selectedLang.setImageResource(Constants.selectedTickIcon)


        if (isApplied?.intAppLangApplied == holder.adapterPosition) {
            holder.binding.selectedLang.visibility = View.VISIBLE
            holder.binding.unselectedLang.visibility = View.GONE
            holder.binding.cardItem.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    Constants.languageCardSelectedItemColor
                )
            )
            holder.binding.layoutItem.background =
                ContextCompat.getDrawable(context!!, Constants.languageLayoutItemColor)
        } else {
            holder.binding.selectedLang.visibility = View.GONE
            holder.binding.unselectedLang.visibility = View.GONE
            holder.binding.cardItem.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    Constants.languageCardUnSelectedItemColor
                )
            )
            holder.binding.layoutItem.background =
                ContextCompat.getDrawable(context!!, Constants.languageCardUnSelectedItemColor)

        }
        if (isListClick) {
            if (isSelectedItem == holder.adapterPosition) {
                holder.binding.selectedLang.visibility = View.VISIBLE
                holder.binding.unselectedLang.visibility = View.GONE
                holder.binding.cardItem.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        Constants.languageCardSelectedItemColor
                    )
                )
                holder.binding.layoutItem.background =
                    ContextCompat.getDrawable(context!!, Constants.languageLayoutItemColor)
            } else {
                holder.binding.selectedLang.visibility = View.GONE
                holder.binding.unselectedLang.visibility = View.GONE
                holder.binding.cardItem.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        Constants.languageCardUnSelectedItemColor
                    )
                )
                holder.binding.layoutItem.background =
                    ContextCompat.getDrawable(context!!, Constants.languageCardUnSelectedItemColor)

            }
        }

        holder.binding.root.setOnClickListener {
            isSelectedItem = holder.adapterPosition
            isListClick = true
            mlistener.onLangItemClick(holder.adapterPosition, currentItem)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return appLanguageList.size
    }

    fun setOnItemClickListener(listener: OnAppLangItemClickListener) {
        mlistener = listener
    }

    class MyViewHolder(val binding: AppLanguagesBinding, listener: OnAppLangItemClickListener) :
        RecyclerView.ViewHolder(binding.root)
}
