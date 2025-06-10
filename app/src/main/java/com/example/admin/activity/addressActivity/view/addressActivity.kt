package com.example.admin.activity.addressActivity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.admin.R
import com.example.admin.activity.cartActivity.view.cartActivity
import com.example.admin.databinding.ActivityAddressBinding
import com.example.admin.utils.DBAddress
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class addressActivity : AppCompatActivity() {

    private var type: String? = null
    lateinit var binding : ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BackBtn.setOnClickListener {
            onBackPressed()
        }
        binding.CheckoutBtn.setOnClickListener {
            InsertAddressData()
            Bottom()

        }
        binding.CheckoutBtn.isEnabled = false

        binding.CheckBoxBtn.setOnClickListener {
            binding.CheckoutBtn.isEnabled=!(binding.CheckoutBtn.isEnabled)
        }
    }

    private fun InsertAddressData() {
        var firebaseDatabas = FirebaseDatabase.getInstance()
        var ref = firebaseDatabas.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        if (binding.RGGroup.getCheckedRadioButtonId() == R.id.HomeRGBtn) {
            type = "Home"
        } else if (binding.RGGroup.getCheckedRadioButtonId() == R.id.OfficeRGBtn) {
            type = "Office"
        }


        var UserAddress = DBAddress(

            binding.NameEdt.text.toString(),
            binding.MobileEdt.text.toString(),
            binding.FlatEdt.text.toString(),
            binding.LandmarkEdt.text.toString(),
            binding.StateEdt.text.toString(),
            binding.CityEdt.text.toString(),
            binding.PincodeEdt.text.toString(),
            type.toString()


        )


        ref.child("Address").child(uid.toString()).push().setValue(UserAddress)

        /*  Toast.makeText(this@AddAddressActivity, "Successfully", Toast.LENGTH_SHORT).show()*/

    }

    private fun Bottom() {
        var dialog = BottomSheetDialog(this)

        var view = layoutInflater.inflate(R.layout.success_bottomshit, null)

        val Donebtn = view.findViewById<Button>(R.id.DoneBtn)


        Donebtn.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(this, cartActivity::class.java)
            startActivity(intent)
        }

        dialog.setCancelable(false)


        dialog.setContentView(view)

        dialog.show()

    }
}

