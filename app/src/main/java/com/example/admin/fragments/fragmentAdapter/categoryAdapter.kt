package com.example.admin.fragments.fragmentAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.activity.viewProductActivity.view.viewProductActivity
import com.example.admin.utils.DBReadProduct
import com.google.android.material.card.MaterialCardView

class categoryAdapter(val activity: FragmentActivity?,val categoryList: ArrayList<DBReadProduct>) : RecyclerView.Adapter<categoryAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_filter_item,parent,false)
        return  ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.name.setText(categoryList.get(position).pname)
        holder.price.setText(categoryList.get(position).pprice)
        Glide.with(activity!!).load(categoryList.get(position).pimage).into(holder.img)

        holder.cardView.setOnClickListener {

            viewProduct(position, activity)
        }
    }

    private fun viewProduct(position: Int, activity: FragmentActivity) {
        var intent = Intent(activity, viewProductActivity::class.java)
        intent.putExtra("p1", categoryList[position].cid)
        intent.putExtra("p2", categoryList[position].pcat)
        intent.putExtra("p3", categoryList[position].pdes)
        intent.putExtra("p4", categoryList[position].pdis)
        intent.putExtra("p5", categoryList[position].pimage)
        intent.putExtra("p6", categoryList[position].pname)
        intent.putExtra("p7", categoryList[position].pprice)
        activity.startActivity(intent)
    }

    override fun getItemCount(): Int {

        return categoryList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView){

        var name = itemView.findViewById<TextView>(R.id.productNameTxtC)
        var price = itemView.findViewById<TextView>(R.id.productPriceTxtC)
        var img = itemView.findViewById<ImageView>(R.id.productImgC)
        var cardView = itemView.findViewById<MaterialCardView>(R.id.cardViewC)

    }
}

