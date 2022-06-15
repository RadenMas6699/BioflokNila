/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radenmas.bioflok_nila.databinding.FragmentTemperatureBinding

/**
 * Created by RadenMas on 22/04/2022.
 */
class TemperatureFragment : Fragment() {

    private lateinit var b: FragmentTemperatureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentTemperatureBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun onClick() {
        b.btnTemp.text = "Stabilkan Suhu Air"
        b.btnTemp.setOnClickListener {
            val dbTemp = FirebaseDatabase.getInstance().reference.child("control").child("temp")
            dbTemp.setValue(1)

            b.lottieLoading.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                b.lottieLoading.visibility = View.GONE
                b.lottieSuccess.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    b.lottieSuccess.visibility = View.GONE
                    dbTemp.setValue(0)
                }, 3000)
            }, 2000)
        }
    }

    private fun initView() {
        FirebaseDatabase.getInstance().reference.child("monitoring").limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snapshot in snapshot.children) {
                            val temp = snapshot.child("temp").value.toString()
                            b.tvValueTemp.text = "$temp \u2103"
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}