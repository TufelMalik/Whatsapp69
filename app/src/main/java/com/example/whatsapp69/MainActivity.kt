package com.example.whatsapp69

import android.R
import android.os.Bundle
import android.text.method.MultiTapKeyListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.whatsapp69.Adapters.ViewPagerAdapter
import com.example.whatsapp69.Fragments.*
import com.example.whatsapp69.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //  Setting the ToolBar on MainActivity

        setSupportActionBar(binding.mainToolBar)

        val fragmentArrayList = ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager,fragmentArrayList)
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)




    }
}