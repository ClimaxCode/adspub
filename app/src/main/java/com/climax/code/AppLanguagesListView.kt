package com.climax.code

import AppLanguagesModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.climax.ads.adsclas.setAppLocale
import com.climax.code.applanguages.AppLanguagesAdapter
import com.climax.code.applanguages.ExtensionFun.languagesList
import com.climax.code.applanguages.PrefStorage
import com.climax.code.databinding.ActivityAppLanguagesListViewBinding

class AppLanguagesListView : AppCompatActivity(), AppLanguagesAdapter.OnAppLangItemClickListener {
    private val binding by lazy {
        ActivityAppLanguagesListViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showLanguagesList()
    }

    private fun showLanguagesList() {
        val adapterLang = AppLanguagesAdapter(languagesList, this)
        binding.recyclerViewLanguagesList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLanguagesList.adapter = adapterLang
    }

    override fun onLangItemClick(position: Int, item: AppLanguagesModel) {
        Toast.makeText(this, "$position --" +
                "${item.countryName}--${item.langCodes}--" +
                "${item.langName}", Toast.LENGTH_SHORT).show()
        PrefStorage(this).intAppLangApplied = position
        setAppLocale(item.langCodes)
    }
}