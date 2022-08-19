package com.app.currency_converter.domain.usecase

import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.DataModels
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.network.model.toDomainModelList
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.data.repositoryImpl.ExchangeRateRepositoryImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class FetchExchangeRatesUseCaseTest {

    private var mockService = mockk<ApiService>(relaxed = true)

    private var currencyDao = mockk<CurrencyDao>(relaxed = true)

    private var appPreference = mockk<AppPreference>(relaxed = true)

    private lateinit var useCase: FetchExchangeRatesUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        val repositoryImpl = ExchangeRateRepositoryImpl(mockService, currencyDao, appPreference)
        useCase = FetchExchangeRatesUseCase(repositoryImpl)
    }

    @Test
    fun `fetch exchange rates success`(){
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false
        coEvery {
            mockService.getLatestExchangeRates(any())
        } returns DataModels.getExchangeRateModel()

        // when
        val result = runBlocking { useCase.execute() }

        // then
        assertThat(result).isEqualTo(FetchExchangeRatesUseCase.Result.Success(DataModels.getExchangeRateModel().toDomainModelList()))
    }

    @Test
    fun `fetch exchange rates failure`() {
        val exception = IOException()

        //given
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false

        coEvery {
            mockService.getLatestExchangeRates(any())
        } throws IOException()

        //when
        val result = runBlocking { useCase.execute() }

        //then
        assertThat(result.toString()).isEqualTo(FetchExchangeRatesUseCase.Result.Error(exception).toString())
    }

    @Test
    fun `fetch exchange rates returns null`() {
        //given
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false

        coEvery {
            mockService.getLatestExchangeRates(any())
        } returns null

        //when
        val result = runBlocking { useCase.execute() }

        //then
        assertThat(result.toString()).isEqualTo(FetchExchangeRatesUseCase.Result.Error(RuntimeException()).toString())
    }

}