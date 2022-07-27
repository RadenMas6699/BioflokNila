/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.FragmentChartBinding
import java.util.*

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
                    chart(b.lineChart, dataTemp, 0, 50)
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
                    chart(b.lineChart, dataPh, 1, 11)
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
                        val turbidity = snapshot.child("turb").value.toString().toFloat()
                        val time = snapshot.child("time").value.toString().toFloat()
                        dataTurbidity.add(Entry(time, turbidity))
                    }
                    chart(b.lineChart, dataTurbidity, 0, 1000)
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

    fun chart(
        lineChart: com.github.mikephil.charting.charts.LineChart,
        values: ArrayList<Entry>,
        min: Int,
        max: Int
    ) {
        val lineDataSet = LineDataSet(null, null)
        lineDataSet.values = values
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawCircles(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawFilled(true)
        lineDataSet.color = ResourcesCompat.getColor(resources, R.color.primary_light, null)
        lineDataSet.lineWidth = 2f
        val iLineDataSets = ArrayList<ILineDataSet>()
        iLineDataSets.add(lineDataSet)
        val lineData = LineData(iLineDataSets)

        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.textSize = 10f;
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawLabels(true)
        xAxis.labelRotationAngle = 0f //45
        xAxis.setLabelCount(7, true)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val longtime = value.toLong()
                val cal =
                    Calendar.getInstance(Locale.getDefault())
                cal.timeInMillis = longtime * 1000
                return DateFormat.format("HH:mm", cal).toString()
            }
        }

        val yAxisL = lineChart.getAxis(YAxis.AxisDependency.LEFT)
        yAxisL.setDrawGridLines(false)
        yAxisL.setDrawLabels(true)
        yAxisL.axisMinimum = min.toFloat()
        yAxisL.axisMaximum = max.toFloat()

        lineChart.data = lineData
        lineChart.axisRight.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.moveViewTo(lineData.entryCount.toFloat(), 50f, YAxis.AxisDependency.LEFT)
    }
}