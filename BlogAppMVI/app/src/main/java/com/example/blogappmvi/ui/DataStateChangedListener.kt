package com.example.blogappmvi.ui

interface DataStateChangedListener {
    fun onDataStateChanged(dataState: DataState<*>?)
}