package com.app.currency_converter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.currency_converter.domain.model.Result
import com.app.currency_converter.domain.repository.ExchangeRateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val exchangeRateRepository: ExchangeRateRepository) :
    ViewModel() {

    val dataLoadedLiveData = MutableLiveData<Unit>()

    suspend fun fetchCurrencies(): Result {
        return exchangeRateRepository.fetchCurrencies()
    }
}