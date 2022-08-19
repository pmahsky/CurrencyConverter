package com.app.currency_converter.utils

import com.app.currency_converter.util.CurrencyConversionUtility
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CurrencyConversionUtilityTest {
    
    private val exchangeRates = mapOf(
            "INR" to 79.79, // Indian Rupee
            "JPY" to 136.46, // Japanese Yen
            "USD" to 1.0, // United States Dollar
    )

    @Test
    fun `test currency conversion from USD to JPY`() {
        val amount = BigDecimal("100")
        val from = exchangeRates["USD"]
        val to = exchangeRates["JPY"]
        val expected = BigDecimal("13646.0")
        val actual = CurrencyConversionUtility.convertCurrency(amount, from!!, to!!)
        assertThat(actual.toDouble()).isEqualTo(expected.toDouble())
    }

    @Test
    fun `test currency conversion from INR to JPY`() {
        val amount = BigDecimal("100")
        val from = exchangeRates["INR"]
        val to = exchangeRates["JPY"]
        val expected = BigDecimal("171.02393783682166")
        val actual = CurrencyConversionUtility.convertCurrency(amount, from!!, to!!)
        assertThat(actual.toDouble()).isEqualTo(expected.toDouble())
    }

}
