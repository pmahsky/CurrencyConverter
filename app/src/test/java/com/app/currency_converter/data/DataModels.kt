package com.app.currency_converter.data

import com.app.currency_converter.data.database.model.CurrencyEntity
import com.app.currency_converter.data.network.model.ExchangeRate

object DataModels {

    internal fun getExchangeRateModel(
        base: String = "USD",
        exchangeRates: Map<String, Double>? = mapOf("USD" to 1.0),
        timestamp: Long? = 123L
    ): ExchangeRate = ExchangeRate(base, exchangeRates, timestamp)

    internal fun getMinimumExchangeRateModel(): ExchangeRate = getExchangeRateModel("USD", null, null)

    internal fun getCurrencyEntity(
        currencyCode: String = "USD",
        exchangeRate: Double = 1.0
    ): CurrencyEntity = CurrencyEntity(currencyCode, exchangeRate)
}