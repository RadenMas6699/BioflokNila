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
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.ActivityMainBinding
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
            in 1..10 -> {
                b.tvTime.text = "Selamat Pagi"
            }
            in 11..14 -> {
                b.tvTime.text = "Selamat Siang"
            }
            in 15..18 -> {
                b.tvTime.text = "Selamat Sore"
            }
            in 19..24 -> {
                b.tvTime.text = "Selamat Malam"
            }
        }

        val navController = findNavController(R.id.fragmentContainerView)
        b.navBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            b.tvTitle.text = destination.label
        }
    }
}