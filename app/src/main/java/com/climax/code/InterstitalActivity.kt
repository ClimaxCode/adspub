package com.climax.code

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.climax.code.databinding.ActivityInterstitalBinding
import com.climax.code.databinding.ActivityMainBinding

class InterstitalActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityInterstitalBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        if (intent.getBooleanExtra("failed",false)){
            binding.txt.text ="Interstital Failed"
        }

    }
}