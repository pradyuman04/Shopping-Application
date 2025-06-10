package com.example.admin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.activity.searchActivity.view.searchActivity
import com.example.admin.databinding.FragmentUserHomeBinding
import com.example.admin.fragments.fragmentAdapter.userDataAdapter
import com.example.admin.fragments.fragmentAdapter.userHomeCatAdapter
import com.example.admin.utils.DBCategory
import com.example.admin.utils.DBReadProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class userHomeFragment : Fragment() {

    lateinit var binding: FragmentUserHomeBinding
    val productList = arrayListOf<DBReadProduct>()
    val productList2 = arrayListOf<DBReadProduct>()
    val productList3 = arrayListOf<DBReadProduct>()
    val productList4 = arrayListOf<DBReadProduct>()
    val productList5 = arrayListOf<DBReadProduct>()
    val categoryList = arrayListOf<DBCategory>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(layoutInflater)

        readCategoryData()
        imageSlider()
        readProductData(1,2,3,4,5)
        search()

        return binding.root
    }

    private fun search() {
        binding.searchImageView.setOnClickListener {
            var intent = Intent(activity, searchActivity::class.java)
            startActivity(intent)

        }
    }

    private fun imageSlider() {

        binding.carousel.registerLifecycle(lifecycle)

        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.one,
            )
        )

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.two
            )
        )

        binding.carousel.setData(list)
    }

    private fun readProductData(i: Int, i1: Int, i2: Int, i3: Int, i4: Int) {


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

                    if (i == cid.toInt()){

                        var productData =
                            DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid, pdis)


                        productList.add(productData)
                    }
                    else if(i1 == cid.toInt()){

                        var productData =
                            DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid, pdis)


                        productList2.add(productData)
                    }else if(i2 == cid.toInt()){

                        var productData =
                            DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid, pdis)


                        productList3.add(productData)
                    }else if(i3 == cid.toInt()){

                        var productData =
                            DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid, pdis)


                        productList4.add(productData)
                    }else if(i4 == cid.toInt()){

                        var productData =
                            DBReadProduct(id, pname, pprice, pdes, pcat, pimage, key, cid, pdis)


                        productList5.add(productData)
                    }
                }
                setProductData(productList,binding.recyclerView1)
                setProductData(productList2,binding.recyclerView2)
                setProductData(productList3,binding.recyclerView3)
                setProductData(productList4,binding.recyclerView4)
                setProductData(productList5,binding.recyclerView5)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun setProductData(productList: ArrayList<DBReadProduct>, recyclerView: RecyclerView) {

        var adpater = userDataAdapter(activity, productList)
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adpater
        recyclerView.layoutManager = layoutManager

    }

    private fun readCategoryData() {

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


                setCategory(categoryList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setCategory(categoryList: ArrayList<DBCategory>) {

        var adapter = userHomeCatAdapter(activity, categoryList)
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.layoutManager = layoutManager
    }
}

