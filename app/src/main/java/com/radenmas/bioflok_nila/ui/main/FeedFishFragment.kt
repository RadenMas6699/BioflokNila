/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.radenmas.bioflok_nila.databinding.FragmentFeedFishBinding

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

    }

    private fun initView() {

    }
}