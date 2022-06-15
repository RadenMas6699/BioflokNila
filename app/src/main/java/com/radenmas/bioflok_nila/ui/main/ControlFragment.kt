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
import androidx.fragment.app.commit
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.FragmentControlBinding

/**
 * Created by RadenMas on 22/04/2022.
 */
class ControlFragment : Fragment() {

    private lateinit var b: FragmentControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentControlBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun onClick() {
        b.tvTemp.setOnClickListener {
            clicked(b.tvTemp, b.tvPh, b.tvTurbidity, b.tvFeed)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentControl, TemperatureFragment())
            transaction?.commit()
        }
        b.tvPh.setOnClickListener {
            clicked(b.tvPh, b.tvTemp, b.tvTurbidity, b.tvFeed)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentControl, PhFragment())
            transaction?.commit()
        }
        b.tvTurbidity.setOnClickListener {
            clicked(b.tvTurbidity, b.tvPh, b.tvTemp, b.tvFeed)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentControl, TurbidityFragment())
            transaction?.commit()
        }
        b.tvFeed.setOnClickListener {
            clicked(b.tvFeed, b.tvTemp, b.tvPh, b.tvTurbidity)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentControl, FeedFishFragment())
            transaction?.commit()
        }
    }

    private fun clicked(tvOn: TextView, tvOff1: TextView, tvOff2: TextView, tvOff3: TextView) {
        tvOn.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
        tvOn.setBackgroundResource(R.drawable.bg_selected)

        tvOff1.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text, null))
        tvOff1.setBackgroundResource(R.drawable.bg_normal)

        tvOff2.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text, null))
        tvOff2.setBackgroundResource(R.drawable.bg_normal)

        tvOff3.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text, null))
        tvOff3.setBackgroundResource(R.drawable.bg_normal)
    }

    private fun initView() {
        clicked(b.tvTemp, b.tvPh, b.tvTurbidity, b.tvFeed)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentControl, TemperatureFragment())
        transaction?.commit()
    }
}