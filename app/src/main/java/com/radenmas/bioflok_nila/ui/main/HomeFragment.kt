/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radenmas.bioflok_nila.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by RadenMas on 22/04/2022.
 */
class HomeFragment : Fragment() {

    private lateinit var b: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun onClick() {

    }

    private fun initView() {
        FirebaseDatabase.getInstance().reference.child("monitoring").limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (snapshot in snapshot.children) {
                        val temp = snapshot.child("temp").value.toString()
                        val ph = snapshot.child("ph").value
                        val turbidity = snapshot.child("turb").value.toString()

                        b.tvValueTemp.text = "$temp \u2103"
                        b.tvValuePh.text = String.format("%.2f", ph)
                        b.tvValueTurbidity.text = turbidity
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        FirebaseDatabase.getInstance().reference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val feed = snapshot.child("feed").value.toString().toLong()

                    val date = Date(feed * 1000)
                    val formatDate = SimpleDateFormat("dd MMM")
                    b.tvValueFeed.text = formatDate.format(date)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}