package com.radenmas.bioflok_nila.ui.utils

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import com.radenmas.bioflok_nila.R
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import java.util.ArrayList

/**
 * Created by RadenMas on 24/04/2022.
 */
class LineChart {
    fun chart(lineChart: LineChart, values: ArrayList<Entry?>, min: Int, max: Int) {
        val lineDataSet = LineDataSet(null, null)
        lineDataSet.values = values
        lineDataSet.cubicIntensity = 1.5f
        lineDataSet.setDrawFilled(true)
        lineDataSet.color = R.color.black
        lineDataSet.lineWidth = 2f
        val iLineDataSets = ArrayList<ILineDataSet>()
        iLineDataSets.add(lineDataSet)
        val lineData = LineData(iLineDataSets)
        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        //        xAxis.setDrawAxisLine(false);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setTextSize(10f);
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawLabels(false)
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