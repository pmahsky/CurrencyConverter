package com.app.currency_converter.domain.repository

import com.app.currency_converter.data.database.model.CurrencyEntity
import com.app.currency_converter.domain.model.Currency

internal interface ExchangeRateRepository {
    var isFirstLaunch: Boolean
    val timestampInSeconds: Long
    suspend fun getAllCurrencies(): List<CurrencyEntity>
    suspend fun getSelectedCurrencies(): List<CurrencyEntity>
    suspend fun upsertCurrency(currency: CurrencyEntity)
    suspend fun upsertCurrencies(currencies: List<CurrencyEntity>)
    suspend fun getCurrency(currencyCode: String): CurrencyEntity
    suspend fun fetchExchangeRates(): List<Currency>
}