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
import com.radenmas.bioflok_nila.databinding.FragmentFeedFishBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by RadenMas on 22/04/2022.
 */
class FeedFishFragment : Fragment() {

    private lateinit var b: FragmentFeedFishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentFeedFishBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun onClick() {
        b.btnFeed.text = "Beri Pakan Ikan"
        b.btnFeed.setOnClickListener {
            val dbFeed = FirebaseDatabase.getInstance().reference.child("control").child("feed")
            dbFeed.setValue(1)

            b.lottieLoading.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                b.lottieLoading.visibility = View.GONE
                b.lottieSuccess.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    b.lottieSuccess.visibility = View.GONE
                    dbFeed.setValue(0)
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
                            val feed = snapshot.child("feed").value.toString().toLong()
                            val date = Date(feed)
                            val formatDate = SimpleDateFormat("dd MMM")
                            b.tvValueFeed.text = formatDate.format(date)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}