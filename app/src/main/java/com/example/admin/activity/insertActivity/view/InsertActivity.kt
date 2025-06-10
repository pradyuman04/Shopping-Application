package com.example.admin.activity.insertActivity.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.admin.activity.productActivity.view.productActivity
import com.example.admin.databinding.ActivityInsertBinding
import com.example.admin.utils.DBCategory
import com.example.admin.utils.DBInsertProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class insertActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertBinding
    var image: Uri? = null
    var uri: Uri? = null
    var list = arrayListOf<DBCategory>()
    var data = arrayOf<String>("Select---------------")
    var category: String = null.toString()
    var cid: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insert()

        uploadImage()

        back()

        readData()
    }

    private fun back() {

        binding.backImage.setOnClickListener {

            onBackPressed()

        }

    }

    private fun uploadImage() {

        binding.uploadImageButton.setOnClickListener {

            var intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, 1)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {


            image = data?.data!!
            if (image != null) {

                binding.imgErrorTxt.isVisible = false
            }
            binding.uploadImage.setImageURI(image)


        }

    }

    private fun insert() {

        binding.insertButton.setOnClickListener {

            if (binding.productNameEditTxt.length() == 0) {

                binding.productNameEditTxt.error = "Product Name Required"

            } else if (binding.productPriceEditTxt.length() == 0) {

                binding.productPriceEditTxt.error = "Product Price Required"

            } else if (binding.productDescriptionEditTxt.length() == 0) {

                binding.productDescriptionEditTxt.error = "Product Description Required"

            } else if (binding.productDiscountEditTxt.length() == 0) {

                binding.productDiscountEditTxt.error = "Product Discount Required"

            } else if (category == null) {

                binding.categoryErrorTxt.isVisible = true

            } else {
                uploadImageToStorage()
            }
        }
    }

    private fun uploadImageToStorage() {

        var file = File(image.toString())

        var storage = Firebase.storage

        var storageReference = storage.reference.child("${file.name}")

        var upload = storageReference.putFile(image!!)


        upload.addOnSuccessListener { snapshot ->

            snapshot.storage.downloadUrl.addOnSuccessListener { result ->

                uri = result
                insertData()


            }

        }.addOnFailureListener {

            Toast.makeText(this, "Failed Storage", Toast.LENGTH_SHORT).show()

        }
    }

    private fun insertData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        val productData = DBInsertProduct(
            binding.productNameEditTxt.text.toString(),
            binding.productPriceEditTxt.text.toString(),
            binding.productDescriptionEditTxt.text.toString(),
            category,
            cid!!,
            uri.toString(),
            binding.productDiscountEditTxt.text.toString()
        )

        databaseReference.child("Product").push().setValue(productData)


        var intent = Intent(this, productActivity::class.java)
        startActivity(intent)
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
                }
            }
    }

}

