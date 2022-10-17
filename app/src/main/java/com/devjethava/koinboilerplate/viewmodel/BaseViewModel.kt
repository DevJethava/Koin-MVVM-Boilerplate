package com.devjethava.koinboilerplate.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devjethava.koinboilerplate.helper.Preference
import com.devjethava.koinboilerplate.model.repository.ApiRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * ParentViewModel
 * All ViewModel parent class
 * contains Common methods for ViewModels
 */
open class BaseViewModel(
    val repository: ApiRepository,
    val preference: Preference
) : ViewModel() {

    val throwError = MutableLiveData<Throwable>()
    val exception: LiveData<Throwable> = throwError

    val compositeDisposable = CompositeDisposable()

    val loading = ObservableBoolean(false)

    fun startLoad() = loading.set(true)
    fun stopLoad() = loading.set(false)

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }
}