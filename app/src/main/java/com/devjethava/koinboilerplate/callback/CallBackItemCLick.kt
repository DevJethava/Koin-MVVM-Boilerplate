package com.devjethava.koinboilerplate.callback

interface CallBackItemCLick<T> {
    fun onItemClick(item: T?, position: Int, message: String)
}