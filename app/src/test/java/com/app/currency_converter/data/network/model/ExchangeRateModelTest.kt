package com.app.currency_converter.data.network.model

import com.app.currency_converter.data.DataModels
import com.app.currency_converter.domain.model.Currency
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class ExchangeRateModelTest {

    @Test
    fun `data model with full data maps to CurrencyDomainModelList`() {
        //given
        val exchangeRateModel = DataModels.getExchangeRateModel()

        //when
        val domainModelList = exchangeRateModel.toDomainModelList()

        //then
        assertThat(domainModelList).isEqualTo(listOf(Currency("USD", 1.0)))
    }

    @Test
    fun `data model with missing data maps to CurrencyDomainModelList`() {
        //given
        val exchangeRateModel = DataModels.getMinimumExchangeRateModel()

        //when
        val domainModelList = exchangeRateModel.toDomainModelList()

        //then
        assertThat(domainModelList).isEqualTo(emptyList<Currency>())
    }
}