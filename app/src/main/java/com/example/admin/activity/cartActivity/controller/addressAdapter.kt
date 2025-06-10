package com.example.admin.activity.cartActivity.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.activity.cartActivity.view.cartActivity
import com.example.admin.utils.DBAddress

class addressAdapter(val activity: cartActivity, val addressList: ArrayList<DBAddress>) :
    RecyclerView.Adapter<addressAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view = LayoutInflater.from(activity).inflate(R.layout.address_item, parent, false)
        return ViewData(view)

    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.TypeText.setText(addressList.get(position).location)
        holder.NameText.setText(addressList.get(position).name)
        holder.AddressText.setText(addressList.get(position).flatno)

        holder.addressRelativeLayout.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {

        return addressList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var TypeText = itemView.findViewById<TextView>(R.id.TypeText)
        var NameText = itemView.findViewById<TextView>(R.id.NameText)
        var AddressText = itemView.findViewById<TextView>(R.id.AddressText)
        var addressRelativeLayout =
            itemView.findViewById<RelativeLayout>(R.id.addressRelativeLayout)

    }

}

    
