package com.example.admin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.admin.activity.mainActivity.view.MainActivity
import com.example.admin.activity.searchActivity.view.searchActivity
import com.example.admin.databinding.FragmentWishlistBinding
import com.example.admin.fragments.fragmentAdapter.wishListAdapter
import com.example.admin.utils.DBReadProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userWishlistFragment : Fragment() {

    lateinit var binding: FragmentWishlistBinding
    val wishList = arrayListOf<DBReadProduct>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(layoutInflater)

        readWishListData()
        back()
        search()

        return binding.root
    }

    private fun readWishListData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        databaseReference.child("WishList").child(uid.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                wishList.clear()

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

                    var wishListData =
                        DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid, pdis)

                    wishList.add(wishListData)
                }

                setWishList(wishList)
            }


            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun setWishList(productList: ArrayList<DBReadProduct>) {

        var adapter = wishListAdapter(activity, productList)
        var layoutManager = GridLayoutManager(activity, 2)
        binding.wishlistRecyclerView.adapter = adapter
        binding.wishlistRecyclerView.layoutManager = layoutManager

    }

    private fun back(){

        binding.imageFrameLayout.setOnClickListener {

            var intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)

        }

    }

    private fun search() {
        binding.searchImageView.setOnClickListener {
            var intent = Intent(activity, searchActivity::class.java)
            startActivity(intent)

        }
    }
}

