package com.example.admin.fragments.fragmentAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.utils.DBCategory

class userHomeCatAdapter(val activity: FragmentActivity?, val categoryList: ArrayList<DBCategory>) :
    RecyclerView.Adapter<userHomeCatAdapter.ViewData>() {

    var imageList = arrayListOf(
        R.drawable.mobile,
        R.drawable.earphone ,
        R.drawable.laptop,
        R.drawable.fashion,
        R.drawable.mobile,
        R.drawable.mobile,
        R.drawable.appliances,
        R.drawable.mobile,
        R.drawable.mobile,
        R.drawable.mobile
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_category_item, parent, false)
        return ViewData(view)

    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.categoryTxt.setText(categoryList[position].cat)
        holder.categoryImageView.setImageResource(imageList[position])
    }

    override fun getItemCount(): Int {

        return categoryList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var categoryImageView = itemView.findViewById<ImageView>(R.id.categoryImageView)
        var categoryTxt = itemView.findViewById<TextView>(R.id.categoryTxt)

    }
}

    
