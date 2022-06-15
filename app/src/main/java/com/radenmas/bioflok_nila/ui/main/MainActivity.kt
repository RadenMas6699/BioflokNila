/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivityMainBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        val dt = Date()
        when (dt.hours) {
            in 0..5 -> {
                b.tvTime.text = "Selamat Malam"
            }
            in 6..10 -> {
                b.tvTime.text = "Selamat Pagi"
            }
            in 11..14 -> {
                b.tvTime.text = "Selamat Siang"
            }
            in 15..18 -> {
                b.tvTime.text = "Selamat Sore"
            }
            in 19..23 -> {
                b.tvTime.text = "Selamat Malam"
            }
        }

        FirebaseDatabase.getInstance().reference.child("monitoring").limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snapshot in snapshot.children) {
                            val time = snapshot.child("time").value.toString().toLong()

                            val date = Date(time)
                            val formatClock = SimpleDateFormat("HH:mm zz")
                            val formatDate = SimpleDateFormat("dd MMM yyyy")

                            b.tvClock.text = formatClock.format(date)
                            b.tvDate.text = formatDate.format(date)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


        val navController = findNavController(R.id.fragmentContainerView)
        b.navBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            b.tvTitle.text = destination.label
        }
    }
}