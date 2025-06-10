package com.example.admin.activity.viewProductActivity.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.admin.activity.cartActivity.view.cartActivity
import com.example.admin.databinding.ActivityViewProductBinding
import com.example.admin.utils.DBCart
import com.example.admin.utils.DBInsertProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class viewProductActivity : AppCompatActivity() {

    private var cid: String? = null
    private var pcat: String? = null
    private var pdes: String? = null
    private var pdis: String? = null
    private var pimage: String? = null
    private var pprice: String? = null
    private var pname: String? = null
    lateinit var binding: ActivityViewProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setData()

        wishlist()

        addToCart()

        back()


    }

    private fun setData() {
        cid = intent.getStringExtra("p1")
        pcat = intent.getStringExtra("p2")
        pdes = intent.getStringExtra("p3")
        pdis = intent.getStringExtra("p4")
        pimage = intent.getStringExtra("p5")
        pname = intent.getStringExtra("p6")
        pprice = intent.getStringExtra("p7")


        Glide.with(this).load(pimage).into(binding.productImg)
        binding.productNameTxt.setText(pname)
        binding.headingText.setText(pname)
        binding.productPriceTxt.setText(pprice)
        binding.productDetailsTxt.setText(pdes)
        binding.productDiscountTxt.setText(pdis)


    }

    private fun wishlist() {

       binding.wishlistImageView.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference

            var firebaseAuth = FirebaseAuth.getInstance()
            var user = firebaseAuth.currentUser
            var uid = user?.uid

            var list = DBInsertProduct(
                pname.toString(),
                pprice.toString(),
                pdes.toString(),
                pcat.toString(),
                cid!!.toInt(),
                pimage.toString(),
                pdis.toString()
            )

            databaseReference.child("WishList").child(uid.toString()).push().setValue(list)

            Toast.makeText(this, "WishListed SuccessFully.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToCart() {

        binding.cartButton.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference

            var firebaseAuth = FirebaseAuth.getInstance()
            var user = firebaseAuth.currentUser
            var uid = user?.uid

            var cartData = DBCart(
                pname.toString(),
                pprice.toString(),
                pdes.toString(),
                pcat.toString(),
                cid!!.toInt(),
                pimage.toString(),
                1

            )

            databaseReference.child("Cart").child(uid.toString()).push().setValue(cartData)

            var intent = Intent(this, cartActivity::class.java)
            startActivity(intent)

        }

    }

    private fun back(){

        binding.imageFrameLayout.setOnClickListener {

           onBackPressed()

        }

    }

}

