package com.devjethava.koinboilerplate.helper

import android.content.Context
import com.devjethava.koinboilerplate.model.data.response.DashboardResponse
import com.google.gson.Gson

class Preference(context: Context) {
    private val prefFileName = "com.devjethava.koinboilerplate"
    private val prfIsLogin = "IsLogin"
    private val prfDashboardResponse = "DashboardResponse"

    private val appLanguage = "appLanguage"
    private val prefResetLanguage = "prefResetLanguage"

    private val preference = context.getSharedPreferences(prefFileName, 0)

    private val gson: Gson = Gson()

    fun clearPreference() {
        preference.edit().clear().apply()
    }

    var isLogin: Boolean
        get() = preference.getBoolean(prfIsLogin, false)
        set(value) = preference.edit().putBoolean(prfIsLogin, value).apply()

    var dashboardResponse: DashboardResponse
        get() = gson.fromJson(
            preference.getString(this.prfDashboardResponse, "").toString(),
            DashboardResponse::class.java
        )
        set(value) = preference.edit().putString(this.prfDashboardResponse, gson.toJson(value)).apply()

    var language: String
        get() = preference.getString(this.appLanguage, "en").toString()
        set(value) = preference.edit().putString(this.appLanguage, value).apply()

    var resetLanguage: Boolean
        get() = preference.getBoolean(prefResetLanguage, false)
        set(value) = preference.edit().putBoolean(prefResetLanguage, value).apply()
}