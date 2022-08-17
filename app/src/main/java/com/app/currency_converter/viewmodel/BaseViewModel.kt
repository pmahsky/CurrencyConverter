package com.app.currency_converter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal abstract class BaseViewModel: ViewModel() {

    val stateMutableLiveData = MutableLiveData<MainViewModel.ViewState>()

    fun fetchData() {
        onFetchData()
    }
    protected abstract fun onFetchData()
}