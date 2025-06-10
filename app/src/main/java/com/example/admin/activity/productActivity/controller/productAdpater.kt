package com.example.admin.activity.productActivity.controller

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.activity.productActivity.view.productActivity
import com.example.admin.activity.updateActivity.view.updateActivity
import com.example.admin.utils.DBInsertProduct
import com.example.admin.utils.DBReadProduct
import com.google.firebase.database.FirebaseDatabase

class productAdpater(
    val activity: productActivity,
    val productInsertList: ArrayList<DBReadProduct>
) :
    RecyclerView.Adapter<productAdpater.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(activity).inflate(R.layout.product_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.productNameTxt.setText(productInsertList[position].pname)
        holder.productPriceTxt.setText(productInsertList[position].pprice)
        holder.productDescriptionTxt.setText(productInsertList[position].pdes)
        holder.productCategoryTxt.setText(productInsertList[position].pcat)
        holder.productDiscountTxt.setText(productInsertList[position].pdis)
        Glide.with(activity).load(productInsertList[position].pimage).into(holder.productImg)

        holder.deleteCardView.setOnClickListener {

            Toast.makeText(activity, "NAI THAI REVA DE...😂😂😂", Toast.LENGTH_SHORT).show()

            //delete(position)
        }

        holder.editCardView.setOnClickListener {

           var intent = Intent(activity,updateActivity::class.java)
            intent.putExtra("n1",productInsertList[position].pname)
            intent.putExtra("n2",productInsertList[position].pprice)
            intent.putExtra("n3",productInsertList[position].pdes)
            intent.putExtra("n4",productInsertList[position].pdis)
            intent.putExtra("n5",productInsertList[position].pimage)
            intent.putExtra("n6",productInsertList[position].key)
            intent.putExtra("n7",productInsertList[position].cid)

            activity.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return productInsertList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productNameTxt = itemView.findViewById<TextView>(R.id.productNameTxt)
        var productPriceTxt = itemView.findViewById<TextView>(R.id.productPriceTxt)
        var productDescriptionTxt = itemView.findViewById<TextView>(R.id.productDescriptionTxt)
        var productDiscountTxt = itemView.findViewById<TextView>(R.id.productDiscountTxt)
        var productCategoryTxt = itemView.findViewById<TextView>(R.id.productCategoryTxt)
        var productImg = itemView.findViewById<ImageView>(R.id.productImg)
        var deleteCardView = itemView.findViewById<CardView>(R.id.deleteCardView)
        var editCardView = itemView.findViewById<CardView>(R.id.editCardView)

    }

    private fun delete(position: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()

        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").child("${productInsertList.get(position).key}")
            .removeValue()

        productInsertList.clear()
    }
}

    
