package com.example.whatsapp69

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.whatsapp69.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Settings")


        binding.telegramLayout.setOnClickListener {
            gotoUrl("https://t.me/malik_tufel_official")
        }
        binding.instaLayout.setOnClickListener {
            gotoUrl("https://www.instagram.com/malik_tufel_official")
        }
        binding.whatsAppLayout.setOnClickListener {
            gotoUrl("https://wa.me/9924753867")
        }


    }

    private fun gotoUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}