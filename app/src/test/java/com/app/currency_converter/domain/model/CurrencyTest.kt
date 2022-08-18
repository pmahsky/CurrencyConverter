package com.app.currency_converter.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class CurrencyTest {

    @Test
    fun `same currency object should return true`(){
         val currency = Currency("USD",0.123456)
        assertThat(currency).isEqualTo(currency)
    }

    @Test
    fun `same currency should return true`() {
        val currencyOne = Currency("USD", 0.123456)
        val currencyTwo = currencyOne.copy()
        assertThat(currencyOne).isNotSameInstanceAs(currencyTwo)
        val currencyC = Currency("USD", 0.123456)
        assertThat(currencyOne).isEqualTo(currencyTwo)
        assertThat(currencyTwo).isEqualTo(currencyC)
    }

    @Test
    fun `currencies with different currency code but same exchange rate should return false`(){
        val currencyOne = Currency("USD", 0.123456)
        val currencyTwo = Currency("EUR", 0.123456)
        assertThat(currencyOne).isNotEqualTo(currencyTwo)
    }
}