package com.app.currency_converter.data.repositoryimpl

import com.app.currency_converter.BuildConfig
import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.DataModels
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.network.model.toDomainModelList
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.data.repositoryImpl.ExchangeRateRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
    fun `getExchangeRates fetches ExchangeRates and maps to Model`() {
        //given
        every { appPreference.isDataEmpty } returns true
        every { appPreference.shouldRefreshData } returns false
        coEvery {
            mockService.getLatestExchangeRates(any())
        } returns DataModels.getExchangeRateModel()

        coEvery {
            currencyDao.upsertCurrencies(any())
        } returns Unit

        //when
        val result = runBlocking { repositoryImpl.fetchExchangeRates() }

        //then
        result shouldBeEqualTo DataModels.getExchangeRateModel().toDomainModelList()
    }

//    @Test
//    fun whenNetworkIsAvailableAndDataIsEmptyShouldReturnSuccess(): Unit = runBlocking {
//        val spy = spyk(repositoryImpl)
//        every { appPreference.isDataEmpty } returns true
//        every { appPreference.shouldRefreshData } returns false
//        coEvery { mockService.getLatestExchangeRates(any()) } returns DataModels.getExchangeRateModel()
//        val expected = Resource.Success
//        val actual = spy.fetchExchangeRates()
//        verify { spy["persistResponse"](any<Response<ApiEndPoint>>()) }
//        assertThat(actual).isInstanceOf(expected.javaClass)
//    }

}