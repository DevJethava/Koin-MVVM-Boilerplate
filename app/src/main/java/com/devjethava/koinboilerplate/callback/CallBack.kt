package com.devjethava.koinboilerplate.callback

interface CallBack<T> {
    fun onSuccess(item: T?)
    fun onFailure()
}