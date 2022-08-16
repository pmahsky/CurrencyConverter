package com.app.currency_converter.domain.repository

import androidx.lifecycle.LiveData
import com.app.currency_converter.data.database.model.CurrencyEntity
import com.app.currency_converter.domain.model.Result

interface ExchangeRateRepository {
    var isFirstLaunch: Boolean
    val timestampInSeconds: Long
    fun getAllCurrencies(): LiveData<MutableList<CurrencyEntity>>
    fun getSelectedCurrencies(): LiveData<MutableList<CurrencyEntity>>
    fun upsertCurrency(currency: CurrencyEntity)
    fun upsertCurrencies(currencies: List<CurrencyEntity>)
    suspend fun getCurrency(currencyCode: String): CurrencyEntity
    suspend fun fetchCurrencies(): Result
}