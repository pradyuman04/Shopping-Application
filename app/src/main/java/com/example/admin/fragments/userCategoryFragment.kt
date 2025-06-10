package com.example.admin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.activity.mainActivity.view.MainActivity
import com.example.admin.activity.searchActivity.view.searchActivity
import com.example.admin.databinding.FragmentCategoryBinding
import com.example.admin.fragments.fragmentAdapter.userCategoryAdpater
import com.example.admin.utils.DBCategory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userCategoryFragment : Fragment() {

    var categoryList = arrayListOf<DBCategory>()
    var no = arrayOf(
        1, 2, 3, 4, 5, 6,7,8,9,10
    )
    companion object{

        lateinit var tbinding: FragmentCategoryBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tbinding = FragmentCategoryBinding.inflate(layoutInflater)

        readProductData()

        back()

        search()

        return tbinding.root
    }

    private fun back(){

        tbinding.imageFrameLayout.setOnClickListener {

            var intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)

        }

    }

    private fun search() {
        tbinding.searchImageView.setOnClickListener {
            var intent = Intent(activity, searchActivity::class.java)
            startActivity(intent)

        }
    }

    private fun readProductData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                categoryList.clear()

                for (x in snapshot.children) {


                    var id = x.child("id").getValue().toString()
                    var cat = x.child("cat").getValue().toString()


                    var productData = DBCategory(id, cat)

                    categoryList.add(productData)
                }

                setupCategory(categoryList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setupCategory(categoryList: ArrayList<DBCategory>) {

        var adapter = userCategoryAdpater(activity, categoryList,no)
        var layoutManager = LinearLayoutManager(activity)
        tbinding.categoryFragmentRecyclerView.adapter = adapter
        tbinding.categoryFragmentRecyclerView.layoutManager = layoutManager

    }
}

