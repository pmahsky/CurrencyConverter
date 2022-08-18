package com.app.currency_converter.data.repositoryimpl

import com.app.currency_converter.BuildConfig
import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.DataModels
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.database.model.CurrencyEntity
import com.app.currency_converter.data.network.model.toDomainModelList
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.data.repositoryImpl.ExchangeRateRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExchangeRepositoryImplTest {

    private var mockService = mockk<ApiService>(relaxed = true)

    private var currencyDao = mockk<CurrencyDao>(relaxed = true)

    private var appPreference = mockk<AppPreference>(relaxed = true)

    private lateinit var repositoryImpl: ExchangeRateRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryImpl = ExchangeRateRepositoryImpl(mockService, currencyDao, appPreference)
    }

    @Test
    fun `getExchangeRates fetches ExchangeRates data is empty returns success`() {
        //given
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false
        coEvery {
            mockService.getLatestExchangeRates(any())
        } returns DataModels.getExchangeRateModel()

//        coEvery {
//            currencyDao.upsertCurrencies(any())
//        } returns Unit

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo DataModels.getExchangeRateModel().toDomainModelList()
    }

    @Test
    fun `getExchangeRates `() {
        //given
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false
        coEvery {
            mockService.getLatestExchangeRates(any())
        } returns null

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo null
    }

    @Test
    fun `getExchangeRates fetches ExchangeRates data should refresh data returns success`() {
        //given
        every { appPreference.isDataEmpty } returns false
        every { appPreference.shouldRefreshData } returns true
        coEvery {
            mockService.getLatestExchangeRates(any())
        } returns DataModels.getExchangeRateModel()

//        coEvery {
//            currencyDao.upsertCurrencies(any())
//        } returns Unit

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo DataModels.getExchangeRateModel().toDomainModelList()
    }

    @Test
    fun `getExchangeRates fetches ExchangeRates is data_empty not should_refresh_data not returns success`() {
        //given
        every { appPreference.isDataEmpty } returns false
        every { appPreference.shouldRefreshData } returns false

        coEvery {
            currencyDao.getAllCurrencies()
        } returns listOf(DataModels.getCurrencyEntity())

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo DataModels.getExchangeRateModel().toDomainModelList()
    }

    @Test
    fun `getExchangeRates fetches ExchangeRates is_data_empty returns error`() {
        //given
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false

        coEvery {
            mockService.getLatestExchangeRates(any())
        } throws UnknownHostException()

        coEvery {
            currencyDao.getAllCurrencies()
        } returns listOf(DataModels.getCurrencyEntity())

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo DataModels.getExchangeRateModel().toDomainModelList()
    }

    @Test
    fun `getExchangeRates fetches ExchangeRates should_refresh_data returns error`() {
        //given
        every { appPreference.isDataEmpty } returns false
        every { appPreference.shouldRefreshData } returns true

        coEvery {
            mockService.getLatestExchangeRates(any())
        } throws UnknownHostException()

        coEvery {
            currencyDao.getAllCurrencies()
        } returns listOf(DataModels.getCurrencyEntity())

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo DataModels.getExchangeRateModel().toDomainModelList()
    }

}