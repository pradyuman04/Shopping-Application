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

class wishListAdapter(val activity: FragmentActivity?, val productList: ArrayList<DBReadProduct>) : RecyclerView.Adapter<wishListAdapter.ViewData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item,parent,false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.name.setText(productList.get(position).pname)
        holder.price.setText(productList.get(position).pprice)
        Glide.with(activity!!).load(productList.get(position).pimage).into(holder.img)
        holder.discount.setText(productList.get(position).pdis)

        holder.cardView.setOnClickListener {

            viewProduct(position, activity)
        }
    }

    private fun viewProduct(position: Int, activity: FragmentActivity) {
        var intent = Intent(activity, viewProductActivity::class.java)
        intent.putExtra("p1", productList[position].cid)
        intent.putExtra("p2", productList[position].pcat)
        intent.putExtra("p3", productList[position].pdes)
        intent.putExtra("p4", productList[position].pdis)
        intent.putExtra("p5", productList[position].pimage)
        intent.putExtra("p6", productList[position].pname)
        intent.putExtra("p7", productList[position].pprice)
        activity.startActivity(intent)
    }

    override fun getItemCount(): Int {

        return productList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView){

        var name = itemView.findViewById<TextView>(R.id.productNameTxtW)
        var price = itemView.findViewById<TextView>(R.id.productPriceTxtW)
        var img = itemView.findViewById<ImageView>(R.id.productImgW)
        var discount = itemView.findViewById<TextView>(R.id.productDiscountTxtW)
        var cardView = itemView.findViewById<MaterialCardView>(R.id.cardViewW)
    }
}
