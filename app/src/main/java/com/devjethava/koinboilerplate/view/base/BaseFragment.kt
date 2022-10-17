package com.devjethava.koinboilerplate.view.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.devjethava.koinboilerplate.helper.toast
import com.devjethava.koinboilerplate.R
import com.devjethava.koinboilerplate.helper.Utils
import com.devjethava.koinboilerplate.viewmodel.BaseViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import retrofit2.HttpException

/**
 * BaseFragment
 * All Fragment parent class
 * contains Common methods for Fragments
 */
open class BaseFragment : Fragment() {
    var superViewModel: BaseViewModel? = null
    var strTag = ""

    fun setParentViewModel(viewModel: BaseViewModel) {
        this.superViewModel = viewModel

    }

    fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(
                    owner,
                    Lifecycle.Event.ON_DESTROY
                )
            )
        )

    fun onBackPress() {
        requireActivity().onBackPressed()
    }

    fun dispatchFailure(error: Throwable?, message: String?, viewModel: BaseViewModel) {
        if (error is HttpException) {
            if (error.code() == 401) {
                viewModel.preference.clearPreference()
                goToHome()
                requireActivity().toast(getString(R.string.validation_session_expired))
            } else {
                val serverMessage = Utils.parseErrorMessage(error)
                if (serverMessage == null) {
                    if (message != null)
                        requireActivity().toast(message)
                } else {
                    requireActivity().toast(serverMessage)
                }
            }
        } else {
            if (message != null)
                requireActivity().toast(message)
        }

    }

    fun goToHome() {}
}