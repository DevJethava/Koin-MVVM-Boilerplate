package com.devjethava.koinboilerplate.view.base

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.devjethava.koinboilerplate.BuildConfig
import com.devjethava.koinboilerplate.R
import com.devjethava.koinboilerplate.helper.Preference
import com.devjethava.koinboilerplate.helper.Utils
import com.devjethava.koinboilerplate.helper.toast
import com.devjethava.koinboilerplate.viewmodel.BaseViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import retrofit2.HttpException
import java.util.*

/**
 * BaseActivity
 * All activity parent class
 * contains Common methods for Activity
 */
open class BaseActivity : AppCompatActivity() {
    var baseViewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!BuildConfig.DEBUG) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun resetLanguage() {
        val preference = Preference(this)
        if (preference.resetLanguage) {
            preference.resetLanguage = false
            attachBaseContext(this)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val preference = Preference(newBase)
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale(preference.language)))
    }

    private fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }

    fun setParentViewModel(viewModel: BaseViewModel) {
        this.baseViewModel = viewModel
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

    fun goToHome() {
    }

    fun dispatchFailure(error: Throwable?, message: String?, viewModel: BaseViewModel) {
        if (error is HttpException) {
            if (error.code() == 401) {
                viewModel.preference.clearPreference()
                goToHome()
                toast(getString(R.string.validation_session_expired))
            } else {
                val serverMessage = Utils.parseErrorMessage(error)
                if (serverMessage == null) {
                    if (message != null)
                        toast(message)
                } else {
                    toast(serverMessage)
                }
            }
        } else {
            if (message != null)
                toast(message)
        }
    }
}