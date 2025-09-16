package com.climax.code


import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.climax.ads.R
import com.climax.ads.adsclas.showLargeNative
import com.climax.code.databinding.ItemDummyBinding
import com.climax.code.databinding.ItemNativeAdRecyclerBinding

class MixedGridAdapter(
    private val context: Activity,
    private val items: List<dummyItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_AD = 1
    }

    inner class ViewHolder(val binding: ItemDummyBinding) : RecyclerView.ViewHolder(binding.root)

    inner class AdViewHolder(val binding: ItemNativeAdRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is dummyItem.RegularItem -> VIEW_TYPE_ITEM
            is dummyItem.AdItem -> VIEW_TYPE_AD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_AD -> {
                val binding = ItemNativeAdRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AdViewHolder(binding)
            }
            else -> {
                val binding = ItemDummyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                holder as ViewHolder
                val item = items[position] as dummyItem.RegularItem
                with(holder.binding) {
                    imageItem.setImageResource(item.imageRes)
                }
            }
            VIEW_TYPE_AD -> {
                holder as AdViewHolder
                context.showLargeNative(
                    "ca-app-pub-3940256099942544/2247696110",
                    "native5",
                    R.layout.native5,
                    holder.binding.adRoot,
                    holder.binding.adContainer5,
                    holder.binding.shimmer5,
                    false,
                    false,
                    { },
                    { },
                    { },R.color.Adscolor, com.climax.code.R.color.white,R.color.sub_color
                )
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
