package com.example.admin.activity.cartActivity.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.activity.cartActivity.view.cartActivity
import com.example.admin.utils.DBCartProduct
import com.example.admin.utils.DBCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class cartAdapter(val activity: cartActivity, val cartList: ArrayList<DBCartProduct>) :
    RecyclerView.Adapter<cartAdapter.ViewData>() {


    private var minteger: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(activity).inflate(R.layout.cart_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.productNameTxt.setText(cartList[position].pname)
        holder.productPriceTxt.setText(cartList[position].pprice)
        holder.productDescriptionTxt.setText(cartList[position].pdes)
        holder.productCategoryTxt.setText(cartList[position].pcat)
        holder.Quntiity.text = cartList[position].qua
        Glide.with(activity).load(cartList[position].pimage).into(holder.productImg)


        holder.increment.setOnClickListener {

            var temp = cartList.get(position).qua.toInt()

            if (temp < 10) {
                minteger = temp + 1
                updateQuantity(position, minteger)

            }
        }

        holder.decrement.setOnClickListener {

            var temp = cartList.get(position).qua.toInt()

            if (temp > 1) {
                minteger = temp - 1
                updateQuantity(position, minteger)
            }
        }

        holder.deleteImageView.setOnClickListener {

            deleteData(position)

        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    private fun updateQuantity(position: Int, qua: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var currentUser = firebaseAuth.currentUser
        var uid = currentUser?.uid


        var up = DBCart(
            cartList.get(position).pname,
            cartList.get(position).pprice,
            cartList.get(position).pdes,
            cartList.get(position).pcat,
            cartList.get(position).cid.toInt(),
            cartList.get(position).pimage,
            qua
        )


        databaseReference.child("Cart").child(uid.toString()).child("${cartList.get(position).key}")
            .setValue(up)

    }

    private fun deleteData(position: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var currentUser = firebaseAuth.currentUser
        var uid = currentUser?.uid

        databaseReference.child("Cart").child(uid.toString()).child("${cartList.get(position).key}").removeValue()

        cartList.clear()

    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var productNameTxt = itemView.findViewById<TextView>(R.id.productNameTxt)
        var productPriceTxt = itemView.findViewById<TextView>(R.id.productPriceTxt)
        var productDescriptionTxt = itemView.findViewById<TextView>(R.id.productDescriptionTxt)
        var productCategoryTxt = itemView.findViewById<TextView>(R.id.productCategoryTxt)
        var productImg = itemView.findViewById<ImageView>(R.id.productImg)
        var increment = itemView.findViewById<ImageView>(R.id.increment)
        var decrement = itemView.findViewById<ImageView>(R.id.decrement)
        var Quntiity = itemView.findViewById<TextView>(R.id.Quntiity)
        var deleteImageView = itemView.findViewById<ImageView>(R.id.deleteImageView)


    }
}

    
