package com.devjethava.koinboilerplate.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devjethava.koinboilerplate.helper.Preference
import com.devjethava.koinboilerplate.model.repository.ApiRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * ParentViewModel
 * All ViewModel parent class
 * contains Common methods for ViewModels
 */
open class BaseViewModel(
    val preference: Preference
) : ViewModel() {

    val throwError = MutableLiveData<Throwable>()
    val exception: LiveData<Throwable> = throwError

    val compositeDisposable = CompositeDisposable()

    val loading = ObservableBoolean(false)

    fun startLoad() = loading.set(true)
    fun stopLoad() = loading.set(false)

    /**
     * This is a scope for all co-routines launched by [BaseViewModel]
     * that will be dispatched in a Pool of Thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.Default)

    /**
     * Cancel all co-routines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        ioScope.coroutineContext.cancel()
    }
}