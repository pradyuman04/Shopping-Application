package com.example.admin.activity.signingActivity.view

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.admin.databinding.ActivitySigningBinding
import com.google.firebase.auth.FirebaseAuth

class signingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySigningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()

        signIn()

        hidePassword()

    }

    private fun login() {

        binding.loginRelativeLayout.setOnClickListener {

            onBackPressed()

        }

    }


    //SignIn with Email
    private fun signIn() {

        binding.signinButton.setOnClickListener {

            if (binding.fullNameEditText.length().equals(0)) {

                binding.nameErrorRelativeLayout.isVisible = true

            } else if (binding.usernameEditText.length().equals(0)) {

                binding.usernameErrorRelativeLayout.isVisible = true

            } else if (binding.emailEditText.length().equals(0)) {

                binding.emailErrorRelativeLayout.isVisible = true

            } else if (binding.passwordEditText.length().equals(0)) {

                binding.passwordErrorRelativeLayout.isVisible = true

            }

            else {

                var firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.createUserWithEmailAndPassword(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString()).addOnSuccessListener { result->

                    Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()

                    onBackPressed()

                }.addOnFailureListener { error->

                    Log.e("TAG", "signIn: ${error.message}", )

                    Toast.makeText(this, "Failed To Create User", Toast.LENGTH_SHORT).show()

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


}

