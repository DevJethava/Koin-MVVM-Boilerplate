package com.devjethava.koinboilerplate.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
open class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    var superViewModel: BaseViewModel? = null
    var strTag = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        initCreateView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearOnDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        clearOnDestroy()
    }

    /**
     * For onCreateView work
     */
    open fun initCreateView() {}

    /**
     * For onViewCreated work
     */
    open fun initViewCreated() {}

    /**
     * For onDestroyView work
     */
    open fun clearOnDestroyView(isBindingClear: Boolean = true) {
        if (isBindingClear)
            _binding = null
    }

    /**
     * For onDestroy work
     */
    open fun clearOnDestroy() {}


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
        requireActivity().onBackPressedDispatcher.onBackPressed()
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