package com.devjethava.koinboilerplate.view.activity.dashboard

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devjethava.koinboilerplate.R
import com.devjethava.koinboilerplate.database.entity.UserEntity
import com.devjethava.koinboilerplate.databinding.ActivityDashboardBinding
import com.devjethava.koinboilerplate.helper.PermissionHelper
import com.devjethava.koinboilerplate.helper.Utils
import com.devjethava.koinboilerplate.helper.toast
import com.devjethava.koinboilerplate.view.base.BaseActivity
import com.devjethava.koinboilerplate.viewmodel.DashboardViewModel
import com.devjethava.koinboilerplate.viewmodel.UserViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(R.layout.activity_dashboard) {

    private val TAG = DashboardActivity::class.java.simpleName
    private val viewModel: DashboardViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()

    private var permissionHelper: PermissionHelper? = null

    override fun initOnCreate() {
        super.initOnCreate()
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

        /**
         * How to call Fragment with HolderActivity
         */
//        Intent(this, HolderActivity::class.java).apply {
//            putExtra(Constants.FRAGMENT, BlankFragment2::class.simpleName)
//            putExtra(Constants.DATA_TO_SEND, BlankFragment2::class.simpleName)
//            startActivity(this)
//        }

        /**
         * PermissionHelper usage
         * uncomment initialization and here you go
         */
//        permissionHelper = PermissionHelper(this)
        permissionHelper?.let {
            if (!it.isPermissionGranted(PermissionHelper.WRITE_EXTERNAL_STORAGE))
                it.requestPermission(arrayOf(PermissionHelper.WRITE_EXTERNAL_STORAGE),
                    PermissionHelper.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE,
                    object : PermissionHelper.OnPermissionRequested {
                        override fun onPermissionResponse(isPermissionGranted: Boolean) {
                            if (isPermissionGranted)
                                getDashboardData()
                        }

                    })
            else {
                // do work if permission is already granted
                toast("Permission is Granted.")
            }
        }

        /**
         * How to user Room DB
         */
        userViewModel.addUserData(
            UserEntity(
                name = UUID.randomUUID().toString().substring(0, 5),
                email = UUID.randomUUID().toString().substring(0, 10),
                city = UUID.randomUUID().toString().substring(0, 5)
            )
        )

        userViewModel.getAllUserList().observe(this) {
            Utils.printLog(TAG, Gson().toJson(it))
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                PermissionHelper.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        )
            permissionHelper?.openSettingsDialog(message = R.string.permission_denied_dialog_info_storage)
    }

    override fun clearOnDestroy() {
        super.clearOnDestroy()
        permissionHelper = null
    }
}