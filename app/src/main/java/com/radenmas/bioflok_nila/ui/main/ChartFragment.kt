/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.FragmentChartBinding
import com.radenmas.bioflok_nila.ui.utils.LineChart

/**
 * Created by RadenMas on 22/04/2022.
 */
class ChartFragment : Fragment() {

    private lateinit var b: FragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentChartBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        /* Interval 1 menit
        1 Jam = 60 Menit
        24 Jam = 1440 Menit
         */

        return v
    }

    private fun onClick() {
        b.tvTemp.setOnClickListener {
            clicked(b.tvTemp, b.tvPh, b.tvTurbidity)
            getDataTemp()
        }
        b.tvPh.setOnClickListener {
            clicked(b.tvPh, b.tvTemp, b.tvTurbidity)
            getDataPh()
        }
        b.tvTurbidity.setOnClickListener {
            clicked(b.tvTurbidity, b.tvPh, b.tvTemp)
            getDataTurbidity()
        }
    }

    private fun getDataTemp() {
        FirebaseDatabase.getInstance().reference.child("monitoring").limitToLast(1440)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataTemp = ArrayList<Entry>()
                    for (snapshot in snapshot.children) {
                        val temp = snapshot.child("temp").value.toString().toFloat()
                        val time = snapshot.child("time").value.toString().toFloat()
                        dataTemp.add(Entry(time, temp))
                    }
                    LineChart().chart(b.lineChart, dataTemp, 0, 50)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun getDataPh() {
        FirebaseDatabase.getInstance().reference.child("monitoring").limitToLast(1440)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataPh = ArrayList<Entry>()
                    for (snapshot in snapshot.children) {
                        val ph = snapshot.child("ph").value.toString().toFloat()
                        val time = snapshot.child("time").value.toString().toFloat()
                        dataPh.add(Entry(time, ph))
                    }
                    LineChart().chart(b.lineChart, dataPh, 5, 9)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun getDataTurbidity() {
        FirebaseDatabase.getInstance().reference.child("monitoring").limitToLast(1440)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataTurbidity = ArrayList<Entry>()
                    for (snapshot in snapshot.children) {
                        val turbidity = snapshot.child("turbidity").value.toString().toFloat()
                        val time = snapshot.child("time").value.toString().toFloat()
                        dataTurbidity.add(Entry(time, turbidity))
                    }
                    LineChart().chart(b.lineChart, dataTurbidity, 0, 100)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun clicked(tvOn: TextView, tvOff1: TextView, tvOff2: TextView) {
        tvOn.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
        tvOn.setBackgroundResource(R.drawable.bg_selected)

        tvOff1.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text, null))
        tvOff1.setBackgroundResource(R.drawable.bg_normal)

        tvOff2.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text, null))
        tvOff2.setBackgroundResource(R.drawable.bg_normal)
    }

    private fun initView() {
        clicked(b.tvTemp, b.tvPh, b.tvTurbidity)
        getDataTemp()
    }
}