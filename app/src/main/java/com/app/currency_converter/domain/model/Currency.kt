package com.app.currency_converter.domain.model

internal data class Currency(
    val currencyCode: String,
    val exchangeRate: Double
) {
    fun getExchangeRate():String = exchangeRate.toString()
}