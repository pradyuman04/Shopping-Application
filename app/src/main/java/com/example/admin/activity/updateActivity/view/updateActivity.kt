package com.example.admin.activity.updateActivity.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.admin.activity.productActivity.view.productActivity
import com.example.admin.databinding.ActivityUpdateBinding
import com.example.admin.utils.DBCategory
import com.example.admin.utils.DBInsertProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class updateActivity : AppCompatActivity() {

    lateinit var image: String
    private var id: String? = null
   private var categoryId: Int? = null
    lateinit var binding: ActivityUpdateBinding
    var image2: Uri? =null
    var cid: Int? = null
    var category: String = null.toString()
    var list = arrayListOf<DBCategory>()
    var data = arrayOf<String>("Select---------------")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        back()

        getData()

        readData()
    }

    private fun back() {

        binding.backImage.setOnClickListener {

            onBackPressed()

        }

    }

    private fun getData() {

        val name = intent.getStringExtra("n1")
        val price = intent.getStringExtra("n2")
        val des = intent.getStringExtra("n3")
        val dis = intent.getStringExtra("n4")
        image = intent.getStringExtra("n5").toString()
        id = intent.getStringExtra("n7")

        categoryId = id!!.toInt()



        binding.productNameEditTxt.setText(name)
        binding.productPriceEditTxt.setText(price)
        binding.productDescriptionEditTxt.setText(des)
        binding.productDiscountEditTxt.setText(dis)
        Glide.with(this).load(image).into(binding.uploadImage)


        updateImage()

        binding.updateButton.setOnClickListener {

            updateData(image)

            var intent = Intent(this, productActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateImage() {

        binding.uploadImageButton.setOnClickListener {

            var intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {

            image2 = data?.data!!
            binding.uploadImage.setImageURI(image2)
            updateImageToStorage()
        }
    }

    private fun updateImageToStorage() {


        var file = File(image2.toString())

        var storage = Firebase.storage

        var storageReference = storage.reference.child("${file.name}")

            var upload = storageReference.putFile(image2!!)

            upload.addOnSuccessListener { snapshot ->

                snapshot.storage.downloadUrl.addOnSuccessListener { result ->


                    var temp = result
                    updateData(temp.toString())

                }
            }.addOnFailureListener {}

    }

    private fun updateData(uri : String) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference
        val key = intent.getStringExtra("n6")


        val productData = DBInsertProduct(
            binding.productNameEditTxt.text.toString(),
            binding.productPriceEditTxt.text.toString(),
            binding.productDescriptionEditTxt.text.toString(),
            category,
            cid!!,
            uri,
            binding.productDiscountEditTxt.text.toString()
        )

        databaseReference.child("Product").child(key!!).setValue(productData)

    }

    private fun readData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()
                data = emptyArray()

                for (x in snapshot.children) {
                    var id = x.child("id").getValue().toString()
                    var cat = x.child("cat").getValue().toString()

                    var category = DBCategory(id, cat)

                    list.add(category)

                    data += x.child("cat").getValue().toString()
                }

                setupSpinner(data)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun setupSpinner(data: Array<String>) {

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, data)
        binding.categorySpinner.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()

        binding.categorySpinner.setSelection(categoryId!! - 1)

        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    category = data[position]

                    cid = position + 1

                    Log.e("TAG", "onItemSelected: $category")

                }

            }
    }
}

