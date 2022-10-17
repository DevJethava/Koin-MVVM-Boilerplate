package com.devjethava.koinboilerplate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devjethava.koinboilerplate.helper.Preference
import com.devjethava.koinboilerplate.helper.async
import com.devjethava.koinboilerplate.model.data.response.DashboardResponse
import com.devjethava.koinboilerplate.model.repository.ApiRepository
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo

class DashboardViewModel(
    repository: ApiRepository,
    preference: Preference
) : BaseViewModel(repository, preference) {
    fun getDashboardData(): Single<DashboardResponse> {
        return repository.getDashboardData().async(0)
            .doOnSuccess { stopLoad() }
            .doOnSubscribe {
                startLoad()
            }
            .doAfterTerminate {
                stopLoad()
            }
    }

    private val _getDashboardData = MutableLiveData<DashboardResponse>()
    val getDashboardResponse: LiveData<DashboardResponse> = _getDashboardData

    fun getDashboardData2() {
        repository.getDashboardData().async(0)
            .doOnSubscribe { startLoad() }
            .doOnTerminate { stopLoad() }
            .subscribe(
                {
                    _getDashboardData.postValue(it)
                },
                {
                    throwError.postValue(it)
                    it.printStackTrace()
                }
            ).addTo(compositeDisposable)
    }
}