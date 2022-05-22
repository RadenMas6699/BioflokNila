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
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.FragmentChartBinding

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

        return v
    }

    private fun onClick() {
        b.tvTemp.setOnClickListener {
            clicked(b.tvTemp, b.tvPh, b.tvTurbidity)
        }
        b.tvPh.setOnClickListener {
            clicked(b.tvPh, b.tvTemp, b.tvTurbidity)
        }
        b.tvTurbidity.setOnClickListener {
            clicked(b.tvTurbidity, b.tvPh, b.tvTemp)
        }
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
    }
}