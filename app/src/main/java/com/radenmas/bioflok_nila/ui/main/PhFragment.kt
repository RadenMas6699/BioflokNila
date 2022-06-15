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
import com.radenmas.bioflok_nila.databinding.FragmentPhBinding

/**
 * Created by RadenMas on 22/04/2022.
 */
class PhFragment : Fragment() {

    private lateinit var b: FragmentPhBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentPhBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun onClick() {
        b.btnPh.text = "Stabilkan pH Air"
        b.btnPh.setOnClickListener {
            val dbPh = FirebaseDatabase.getInstance().reference.child("control").child("ph")
            dbPh.setValue(1)

            b.lottieLoading.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                b.lottieLoading.visibility = View.GONE
                b.lottieSuccess.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    b.lottieSuccess.visibility = View.GONE
                    dbPh.setValue(0)
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
                            val ph = snapshot.child("ph").value.toString()
                            b.tvValuePh.text = ph
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}