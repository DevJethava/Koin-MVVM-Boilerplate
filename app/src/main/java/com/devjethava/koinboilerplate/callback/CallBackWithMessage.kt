package com.devjethava.koinboilerplate.callback

interface CallBackWithMessage<T> {
    fun onSuccess(item: T?)
    fun onFailure(message: String?)
}