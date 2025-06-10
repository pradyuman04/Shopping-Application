package com.example.admin.fragments.fragmentAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.fragments.userCategoryFragment
import com.example.admin.utils.DBCategory
import com.example.admin.utils.DBReadProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userCategoryAdpater(
    val activity: FragmentActivity?,
    val categoryList: ArrayList<DBCategory>,
    val no: Array<Int>
) : RecyclerView.Adapter<userCategoryAdpater.ViewData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.categoty_item,parent,false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.categoryTxt.setText(categoryList[position].cat)
        catFilter(1)

        holder.card.setOnClickListener {

        catFilter(no.get(position))

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView){

        var categoryTxt = itemView.findViewById<TextView>(R.id.categoryTxt)
        var card = itemView.findViewById<CardView>(R.id.card)

    }

    private fun catFilter(i: Int) {
        val productList = arrayListOf<DBReadProduct>()


        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                productList.clear()

                for (x in snapshot.children) {


                    var id = x.child("pid").getValue().toString()
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pdes = x.child("pdes").getValue().toString()
                    var pcat = x.child("pcat").getValue().toString()
                    var pimage = x.child("pimage").getValue().toString()
                    var cid = x.child("cid").getValue().toString()
                    var key = x.key.toString()
                    var pdis = x.child("pdis").getValue().toString()


                    if (i == cid.toInt() ){

                        var productData = DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid,pdis)

                        productList.add(productData)

                    }

                }

                setupCategory(productList)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setupCategory(categoryList: ArrayList<DBReadProduct>) {

        var adapter = categoryAdapter(activity, categoryList)
        var layoutManager = GridLayoutManager(activity,2)
        userCategoryFragment.tbinding.CategoryProductRvView.adapter = adapter
        userCategoryFragment.tbinding.CategoryProductRvView.layoutManager = layoutManager

    }

}

