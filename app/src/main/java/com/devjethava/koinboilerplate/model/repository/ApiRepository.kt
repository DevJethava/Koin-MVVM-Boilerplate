package com.devjethava.koinboilerplate.model.repository

import com.devjethava.koinboilerplate.model.data.response.DashboardResponse
import com.devjethava.koinboilerplate.model.remote.RestApiService
import io.reactivex.Single

interface ApiRepository {
    fun getDashboardData(): Single<DashboardResponse>
}

class ApiRepositoryImpl constructor(
    private val remote: RestApiService,
) : ApiRepository {
    override fun getDashboardData(): Single<DashboardResponse> = remote.getDashboardData()
}

