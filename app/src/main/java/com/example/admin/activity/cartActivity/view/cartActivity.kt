package com.example.admin.activity.cartActivity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.activity.cartActivity.controller.cartAdapter
import com.example.admin.databinding.ActivityCartBinding
import com.example.admin.utils.DBCartProduct
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.admin.R
import com.example.admin.activity.addressActivity.view.addressActivity
import com.example.admin.activity.cartActivity.controller.addressAdapter
import com.example.admin.activity.searchActivity.view.searchActivity
import com.example.admin.utils.DBAddress


class cartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    val cartList = arrayListOf<DBCartProduct>()
    var addressList = arrayListOf<DBAddress>()
    var total : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readProductData()

        buyProduct()

        back()

        search()

        readAddress()

    }

    private fun buyProduct() {
        binding.placeOrderButton.setOnClickListener {

            bottomNavigation()
        }
    }

    private fun readProductData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        databaseReference.child("Cart").child(uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                cartList.clear()
                total=0;

                for (x in snapshot.children) {

                    var id = x.child("pid").getValue().toString()
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pdes = x.child("pdes").getValue().toString()
                    var pcat = x.child("pcat").getValue().toString()
                    var pimage = x.child("pimage").getValue().toString()
                    var cid = x.child("cid").getValue().toString()
                    var qua = x.child("qua").getValue().toString()
                    var key = x.key.toString()

                    var productData = DBCartProduct(id, pname, pprice, pdes, pcat, pimage, key, cid,qua)

                    total = (pprice!!.toInt()*qua.toInt()) + total

                    cartList.add(productData)
                }

                binding.totalTxt.setText(total.toString())

                setProductData(cartList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun setProductData(productList: ArrayList<DBCartProduct>) {

        var adpater = cartAdapter(this, productList)
        var layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = adpater
        binding.cartRecyclerView.layoutManager = layoutManager

    }

    private fun bottomNavigation(){

       var dialog  = BottomSheetDialog(this)
        dialog.setContentView(R.layout.dialog_bottomsheet)

        dialog.show()

        val btnClose = dialog.findViewById<ImageView>(R.id.CloseBtn)
        var Addressbtn = dialog.findViewById<TextView>(R.id.AddAddress)
        var addressRecyclerView = dialog.findViewById<RecyclerView>(R.id.AddressRvSetup)

        var adpater = addressAdapter(this, addressList)
        var layoutManager = LinearLayoutManager(this)
        addressRecyclerView?.adapter = adpater
        addressRecyclerView?.layoutManager = layoutManager


        Addressbtn?.setOnClickListener {
            var intent = Intent(this, addressActivity::class.java)
            startActivity(intent)

            dialog.dismiss()
        }

    }

    private fun back() {

        binding.imageFrameLayout.setOnClickListener {

            onBackPressed()
        }
    }

    private fun search() {
        binding.searchImageView.setOnClickListener {

            var intent = Intent(this, searchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun readAddress(){

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

        databaseReference.child("Address").child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               addressList.clear()

                for(x in snapshot.children){

                    var name = x.child("name").getValue().toString()
                    var mobile = x.child("mobile").getValue().toString()
                    var flatno = x.child("flatno").getValue().toString()
                    var landmark = x.child("landmark").getValue().toString()
                    var state = x.child("state").getValue().toString()
                    var city = x.child("city").getValue().toString()
                    var pincode = x.child("pincode").getValue().toString()
                    var location = x.child("location").getValue().toString()

                    var address = DBAddress(name, mobile, flatno, landmark, state, city, pincode, location)

                    addressList.add(address)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

