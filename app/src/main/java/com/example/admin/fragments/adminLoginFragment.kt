package com.example.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.R
import com.example.admin.activity.productActivity.view.productActivity
import com.example.admin.databinding.FragmentAdminLoginBinding

class adminLoginFragment : Fragment() {

    lateinit var binding: FragmentAdminLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminLoginBinding.inflate(layoutInflater)

        binding.loginButton222.setOnClickListener {

            var intent = Intent(activity,productActivity::class.java)
            startActivity(intent)

        }

        return binding.root
    }


}

