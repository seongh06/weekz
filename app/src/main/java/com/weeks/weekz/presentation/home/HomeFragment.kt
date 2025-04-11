package com.weeks.weekz.presentation.home

import androidx.navigation.NavController
import com.weeks.weekz.R
import com.weeks.weekz.databinding.FragmentHomeBinding
import com.weeks.weekz.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var navController: NavController
    override fun initView() {

    }

    override fun initObserver() {

    }
}