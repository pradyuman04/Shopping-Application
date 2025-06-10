package com.example.admin.activity.loginActivity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.admin.activity.loginActivity.controller.TabLayoutAdapter
import com.example.admin.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout

class loginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayout()
    }


    private fun setTabLayout(){

        binding.loginTabLayout.addTab(binding.loginTabLayout.newTab().setText("User"))
        binding.loginTabLayout.addTab(binding.loginTabLayout.newTab().setText("Admin"))

        var adapter = TabLayoutAdapter(this,supportFragmentManager)
        binding.loginViewPager.adapter = adapter

        binding.loginViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.loginTabLayout))

        binding.loginTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.loginViewPager.currentItem  = tab?.position!!
             }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


    }
}

