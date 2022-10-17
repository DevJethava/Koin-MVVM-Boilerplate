package com.devjethava.koinboilerplate.view.activity.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devjethava.koinboilerplate.R
import com.devjethava.koinboilerplate.databinding.ActivityDashboardBinding
import com.devjethava.koinboilerplate.databinding.ActivityHolderBinding
import com.devjethava.koinboilerplate.helper.Constants
import com.devjethava.koinboilerplate.helper.Utils
import com.devjethava.koinboilerplate.helper.toast
import com.devjethava.koinboilerplate.view.activity.HolderActivity
import com.devjethava.koinboilerplate.view.base.BaseActivity
import com.devjethava.koinboilerplate.view.fragment.BlankFragment
import com.devjethava.koinboilerplate.view.fragment.BlankFragment2
import com.devjethava.koinboilerplate.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {

    private val TAG = DashboardActivity::class.java.simpleName
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel by viewModel<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    /**
     * For Initial work
     */
    private fun init() {
        getDashboardData()

        binding.btnRefresh.setOnClickListener {
            clearView()
            getDashboardData()
        }

        /**
         * 2: type of call API from ViewModel
          */
//        viewModel.getDashboardData2()
//        viewModel.getDashboardResponse.observe(this) {
//            it?.let { response -> {
//
//            } }
//        }
//
//        viewModel.exception.observe(this) {
//            Utils.printLog(TAG, it.toString())
//        }

        /**
         * open fragment and Follow Only One Activity architecture
         */
//        Intent(this, HolderActivity::class.java).apply {
//            putExtra(Constants.FRAGMENT, BlankFragment::class.java.simpleName)
//            startActivity(this)
//        }
//
//        Intent(this, HolderActivity::class.java).apply {
//            putExtra(Constants.FRAGMENT, BlankFragment2::class.java.simpleName)
//            putExtra(Constants.DATA_TO_SEND, BlankFragment2::class.java.simpleName)
//            startActivity(this)
//        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDashboardData() {
        if (Utils.isConnectionAvailable(this)) {
            val progressDialog: AlertDialog = Utils.setupLoadingDialog(this)
            progressDialog.show()
            viewModel.getDashboardData().bindLifeCycle(this).subscribe({
                progressDialog.dismiss()
                it?.let { response ->
                    if (response.results.isNullOrEmpty()) {
                        toast(getString(R.string.msg_try_again))
                    } else {
                        val item = response.results.first()
                        item.let { data ->
                            data.picture?.large?.let { profile ->
                                Utils.loadImage(this, binding.ivUserProfile, profile)
                            }
                            binding.etName.setText(data.name?.first.toString() + " " + data.name?.last)
                            binding.etEmail.setText(data.email.toString())
                            binding.etAge.setText(data.dob?.age.toString())
                            binding.etAddress.setText(data.location?.city + " ," + data.location?.country + " ," + data.location?.postcode)
                            binding.etPhoneNumber.setText(data.phone)
                        }
                    }
                }
            }, {
                Utils.printLog(TAG, it.toString())
                progressDialog.dismiss()
                dispatchFailure(it, null, viewModel)
            })
        } else {
            toast(getString(R.string.internet_not_available))
        }
    }

    private fun clearView() {
        binding.ivUserProfile.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_launcher_background
            )
        )
        binding.etName.setText("")
        binding.etEmail.setText("")
        binding.etAge.setText("")
        binding.etAddress.setText("")
        binding.etPhoneNumber.setText("")
    }
}