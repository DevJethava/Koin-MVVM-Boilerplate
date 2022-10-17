package com.devjethava.koinboilerplate.model.remote

import com.devjethava.koinboilerplate.model.data.response.DashboardResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RestApiService {
    // Get Dashboard
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("1.4")
    fun getDashboardData(): Single<DashboardResponse>
}