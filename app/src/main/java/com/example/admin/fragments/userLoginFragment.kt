package com.example.admin.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.admin.activity.loginActivity.view.loginActivity
import com.example.admin.activity.mainActivity.view.MainActivity
import com.example.admin.activity.signingActivity.view.signingActivity
import com.example.admin.databinding.FragmentUserLoginBinding
import com.google.firebase.auth.FirebaseAuth

class userLoginFragment : Fragment() {

    lateinit var binding: FragmentUserLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserLoginBinding.inflate(layoutInflater)

        hidePassword()

        loginWithPassword()

        signing()

        return binding.root
    }

    private fun loginWithPassword() {

        binding.loginButton222.setOnClickListener {

            if (binding.emailEditText.length().equals(0)) {

                binding.emailErrorRelativeLayout.isVisible = true

            } else if (binding.passwordEditText.length().equals(0)) {

                binding.passwordErrorRelativeLayout.isVisible = true

            } else {
                var firebaseAuth = FirebaseAuth.getInstance()

                firebaseAuth.signInWithEmailAndPassword(

                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()

                ).addOnSuccessListener {

                    Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()

                    var intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    loginActivity().finish()

                }.addOnFailureListener {

                    Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show()

                }
            }
       }
    }

    private fun hidePassword() {

        binding.passwordCheckBox.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(button: CompoundButton?, boolean: Boolean) {
                if (boolean) {

                    binding.passwordEditText.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()

                } else {

                    binding.passwordEditText.transformationMethod =
                        PasswordTransformationMethod.getInstance()

                }
            }
        })

    }

    private fun signing() {

        binding.signingRelativeLayout.setOnClickListener {

            var intent = Intent(activity, signingActivity::class.java)
            startActivity(intent)

        }
    }

}

