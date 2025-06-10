package com.example.admin.activity.productActivity.view

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.activity.insertActivity.view.insertActivity
import com.example.admin.activity.productActivity.controller.productAdpater
import com.example.admin.databinding.ActivityProductBinding
import com.example.admin.utils.DBCategory
import com.example.admin.utils.DBReadProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class productActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductBinding

    val productList = arrayListOf<DBReadProduct>()
    var cat: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)

        setContentView(binding.root)

        readProductData()

        insertData()

        addCategory()
    }

    private fun insertData() {

        binding.insertButton.setOnClickListener {

            var intent = Intent(this, insertActivity::class.java)
            startActivity(intent)
        }
    }

    private fun readProductData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                productList.clear()

                for (x in snapshot.children) {


                    var id = x.child("pid").getValue().toString()
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pdes = x.child("pdes").getValue().toString()
                    var pcat = x.child("pcat").getValue().toString()
                    var pimage = x.child("pimage").getValue().toString()
                    var cid = x.child("cid").getValue().toString()
                    var key = x.key.toString()
                    var pdis = x.child("pdis").getValue().toString()

                    var productData = DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid,pdis)

                    productList.add(productData)
                }

                setProductData(productList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun setProductData(productList: ArrayList<DBReadProduct>) {

        var adpater = productAdpater(this, productList)
        var layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adpater
        binding.recyclerView.layoutManager = layoutManager

    }

    private fun addCategory() {

        binding.addCategoryImage.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference

            var dialog = Dialog(this)
            dialog.setContentView(R.layout.update_category_dialog)

            dialog.show()

            var categoryIdEditTxt = dialog.findViewById<EditText>(R.id.categoryIdEditTxt)
            var categoryNameEditTxt = dialog.findViewById<EditText>(R.id.categoryNameEditTxt)
            var addCategoryButton = dialog.findViewById<Button>(R.id.addCategoryButton)
            var categoryNumTxt = dialog.findViewById<TextView>(R.id.categoryNumTxt)

            databaseReference.child("Category").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (x in snapshot.children) {

                        var category = x.child("id").getValue().toString()

                        cat = category.toInt()
                    }

                    categoryNumTxt.text = (cat!! + 1).toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            addCategoryButton.setOnClickListener {

                if (categoryIdEditTxt.length() == 0) {

                    categoryIdEditTxt.error = "Id"

                } else if (categoryNameEditTxt.length() == 0) {

                    categoryNameEditTxt.error = "Name"
                } else {
                    var dbCategory = DBCategory(
                        categoryIdEditTxt.text.toString(),
                        categoryNameEditTxt.text.toString()
                    )

                    databaseReference.child("Category").push().setValue(dbCategory)

                    dialog.dismiss()

                    Toast.makeText(this, "Category Added Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

