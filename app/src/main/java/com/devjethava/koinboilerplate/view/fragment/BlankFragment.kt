package com.devjethava.koinboilerplate.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.devjethava.koinboilerplate.R
import com.devjethava.koinboilerplate.databinding.FragmentBlankBinding
import com.devjethava.koinboilerplate.view.base.BaseFragment
import com.devjethava.koinboilerplate.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : BaseFragment<FragmentBlankBinding>(R.layout.fragment_blank) {

    private val TAG = BlankFragment::class.simpleName

    init {
        strTag = TAG!!
    }

    private val viewModel by viewModel<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun initCreateView() {
        super.initCreateView()
        binding.fragment = this
        binding.vm = viewModel
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment BlankFragment.
         */
        @JvmStatic
        fun newInstance() =
            BlankFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}