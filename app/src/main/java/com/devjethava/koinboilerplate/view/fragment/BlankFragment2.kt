package com.devjethava.koinboilerplate.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.devjethava.koinboilerplate.helper.toast
import com.devjethava.koinboilerplate.R
import com.devjethava.koinboilerplate.databinding.FragmentBlank2Binding
import com.devjethava.koinboilerplate.view.base.BaseFragment
import com.devjethava.koinboilerplate.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : BaseFragment<FragmentBlank2Binding>(R.layout.fragment_blank2) {

    private val TAG = BlankFragment2::class.simpleName

    init {
        strTag = TAG!!
    }

    private val viewModel by viewModel<DashboardViewModel>()

    var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun initCreateView() {
        super.initCreateView()
        binding.fragment = this
        binding.vm = viewModel

        requireActivity().toast(param1.toString())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment BlankFragment2.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            BlankFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}