package com.example.baseproject.presentation.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.baseproject.R
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.base.delegate.viewBinding
import com.example.baseproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mBinding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.test.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSettingFragment(
                    ""
                )
            )
        }
    }
}