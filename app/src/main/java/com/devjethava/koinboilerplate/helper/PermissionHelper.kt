package com.devjethava.koinboilerplate.helper

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.devjethava.koinboilerplate.callback.CallBack
import com.devjethava.koinboilerplate.R

/**
 * [PermissionHelper] class to take care of all the Runtime - Permission required by an app.
 */

class PermissionHelper(private val activity: Activity) :
    ActivityCompat.OnRequestPermissionsResultCallback {
    interface OnPermissionRequested {
        fun onPermissionResponse(isPermissionGranted: Boolean)
    }

    private var listener: OnPermissionRequested? = null

    fun isPermissionGranted(permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.applicationContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }

    fun requestPermission(
        permissions: Array<String>,
        requestCode: Int,
        listener: OnPermissionRequested
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.listener = listener
            activity.requestPermissions(permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val isPermissionGranted =
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (!isPermissionGranted) {
            val should =
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])
            if (should) additionalInfoDialog(
                permissionCode = requestCode,
                permissionArray = permissions.toList().toTypedArray()
            )
            else listener?.onPermissionResponse(false)
        } else listener?.onPermissionResponse(isPermissionGranted)
    }

    private fun additionalInfoDialog(
        @StringRes message: Int? = null,
        permissionCode: Int,
        @StringRes positiveButton: Int? = null,
        @StringRes negativeButton: Int? = null,
        permissionArray: Array<String>
    ) {
        val should =
            ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (should) {
            val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
            builder.setTitle(R.string.permission_denied)
            builder.setMessage(message ?: R.string.access_fine_loc)
            builder.setPositiveButton(positiveButton ?: R.string.i_am_sure) { dialog, _ ->
                listener?.onPermissionResponse(false)
                openSettingsDialog()
                dialog.dismiss()
            }
            builder.setNegativeButton(negativeButton ?: R.string.re_try) { dialog, _ ->
                dialog.dismiss()
                ActivityCompat.requestPermissions(
                    activity,
                    permissionArray,
                    permissionCode
                )
            }
            builder.show()
        }
    }

    fun openSettingsDialog(
        @StringRes message: Int? = null
    ) {
        Utils.showPositiveDialogWithListener(
            activity,
            activity.resources.getString(R.string.permission_required_dialog_title),
            activity.resources.getString(message ?: R.string.permission_denied_dialog_info),
            object : CallBack<String> {
                override fun onSuccess(item: String?) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.fromParts(packageText, activity.packageName, null)
                    activity.startActivity(intent)
                }

                override fun onFailure() {

                }

            },
            activity.resources.getString(R.string.open_settings), false
        )
    }

    companion object {
        const val packageText = "package"

        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        const val PERMISSIONS_REQUEST_LOCATION = 101
        const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102
    }
}