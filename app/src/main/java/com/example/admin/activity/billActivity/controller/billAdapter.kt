package com.example.admin.activity.billActivity.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.activity.billActivity.view.billActivity
import com.example.admin.utils.DBCartProduct

class billAdapter(val activity: billActivity, val billList: ArrayList<DBCartProduct>) : RecyclerView.Adapter<billAdapter.ViewData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.productNameTxt.setText(billList[position].pname)
        holder.productPriceTxt.setText(billList[position].pprice)
        holder.productDescriptionTxt.setText(billList[position].pdes)
        holder.productCategoryTxt.setText(billList[position].pcat)
        holder.Quntiity.text = billList[position].qua
        Glide.with(activity).load(billList[position].pimage).into(holder.productImg)


    }

    override fun getItemCount(): Int {

        return billList.size
    }
    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView){

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

