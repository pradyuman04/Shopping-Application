package com.example.admin.activity.loginActivity.controller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.admin.activity.loginActivity.view.loginActivity
import com.example.admin.fragments.adminLoginFragment
import com.example.admin.fragments.userLoginFragment

class TabLayoutAdapter(val activity: loginActivity,val supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){

        0-> userLoginFragment()

            else-> adminLoginFragment()

        }

    }
}

