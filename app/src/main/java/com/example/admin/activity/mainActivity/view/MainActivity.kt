package com.example.admin.activity.mainActivity.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.admin.R
import com.example.admin.activity.cartActivity.view.cartActivity
import com.example.admin.databinding.ActivityMainBinding
import com.example.admin.fragments.userCategoryFragment
import com.example.admin.fragments.userHomeFragment
import com.example.admin.fragments.useraccountFragment
import com.example.admin.fragments.userWishlistFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(userHomeFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeImg -> loadFragment(userHomeFragment())
                R.id.categoryImg -> loadFragment(userCategoryFragment())
                R.id.userImg -> loadFragment(useraccountFragment())
                R.id.wishlistImg -> loadFragment(userWishlistFragment())
                R.id.cartImg -> changeActivity(cartActivity())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Viewpager, fragment)
            commit()
        }
    }

    private fun changeActivity(activity: Activity) {

        var intent = Intent(this, activity::class.java)
        startActivity(intent)

    }
}

