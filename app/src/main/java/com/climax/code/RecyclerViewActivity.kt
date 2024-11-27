package com.climax.code

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.climax.code.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRecyclerViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }
        var dummyItemlist = ArrayList<dummyItem>()

        for (i in 1..100) {
            // Add a regular item
            dummyItemlist.add(dummyItem.RegularItem(R.drawable.img1, "Programmatic Item #$i"))

            // Add an ad after every 4th row (i.e., after every 16th item in a 4-column grid)
            if (i == 8 ) {
                dummyItemlist.add(dummyItem.AdItem) // Replace with your AdItem logic
            }
        }

        val adapter = MixedGridAdapter(this,dummyItemlist)
        var gridmanger =GridLayoutManager(this,4)
        binding.recyclerView.adapter = adapter
        gridmanger.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Full span for ads, 1 span for regular items
                return if (adapter.getItemViewType(position) == MixedGridAdapter.VIEW_TYPE_AD) {
                    gridmanger.spanCount // Full span (4 columns)
                } else {
                    1 // Regular item (single span)
                }
            }
        }
        binding.recyclerView.layoutManager = gridmanger
    }
}
sealed class dummyItem {
    data class RegularItem(val imageRes: Int, val title: String) : dummyItem()
    object AdItem : dummyItem()
}
