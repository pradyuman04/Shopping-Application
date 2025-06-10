package com.example.admin.activity.billActivity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.activity.billActivity.controller.billAdapter
import com.example.admin.databinding.ActivityBillBinding
import com.example.admin.utils.DBCartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class billActivity : AppCompatActivity() {

    lateinit var binding: ActivityBillBinding
    val billList = arrayListOf<DBCartProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readProductData()

    }

    private fun readProductData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        databaseReference.child("Cart").child(uid.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                billList.clear()

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
                    billList.add(productData)
                }

                setProductData(billList)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun setProductData(billList: ArrayList<DBCartProduct>) {

        var adpater = billAdapter(this, billList)
        var layoutManager = LinearLayoutManager(this)
        binding.billRecyclerView.adapter = adpater
        binding.billRecyclerView.layoutManager = layoutManager

    }


}

