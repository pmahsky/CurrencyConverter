package com.app.currency_converter.presentation.viewmodel

import com.app.currency_converter.BuildConfig
import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.DataModels
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.network.model.toDomainModelList
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.data.repositoryImpl.ExchangeRateRepositoryImpl
import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.domain.usecase.FetchExchangeRatesUseCase
import com.app.currency_converter.domain.usecase.GetConvertedExchangeRatesUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @JvmField
    @RegisterExtension
    var instantTaskExecutorExtension = InstantTaskExecutorExtension()

    private var mockService = mockk<ApiService>(relaxed = true)
    private var currencyDao = mockk<CurrencyDao>(relaxed = true)
    private var appPreference = mockk<AppPreference>(relaxed = true)
    private lateinit var fetchExchangeRatesUseCase: FetchExchangeRatesUseCase
    private lateinit var getConvertedExchangeRatesUseCase: GetConvertedExchangeRatesUseCase

    private lateinit var viewModel: MainViewModel
    private val standardTestDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(standardTestDispatcher)
        val repositoryImpl = ExchangeRateRepositoryImpl(mockService, currencyDao, appPreference)
        fetchExchangeRatesUseCase = FetchExchangeRatesUseCase(repositoryImpl)
        getConvertedExchangeRatesUseCase = GetConvertedExchangeRatesUseCase(currencyDao)
        viewModel = MainViewModel(fetchExchangeRatesUseCase,getConvertedExchangeRatesUseCase)
    }

//    @Test
//    fun `execute fetchExchangeRateUseCase`(){
//        //when
//        every { appPreference.isDataEmpty } returns true
//        every { appPreference.shouldRefreshData } returns false
//        viewModel.fetchData()
//
//        //then
//        coVerify { fetchExchangeRatesUseCase.execute() }
//    }

    @Test
    fun `verify state when FetchExchangeRateUseCase returns empty list success`() {

        every { appPreference.shouldRefreshData } returns true
        every { appPreference.isDataEmpty } returns true
        // given
        coEvery { fetchExchangeRatesUseCase.execute() } returns FetchExchangeRatesUseCase.Result.Success(emptyList())

        // when
        runTest {
            viewModel.fetchData()
        }

        assertThat(viewModel.stateMutableLiveData.value).isEqualTo(MainViewModel.ViewState(
            isLoading = false,
            isError = true,
            currencyList = listOf()
        ))
    }

    @Test
    fun `verify state when FetchExchangeRateUseCase returns failure`() {

        every { appPreference.shouldRefreshData } returns true
        every { appPreference.isDataEmpty } returns true
        // given
        coEvery { fetchExchangeRatesUseCase.execute() } returns FetchExchangeRatesUseCase.Result.Error(
            Throwable()
        )

        // when
        runTest {
            viewModel.fetchData()
        }

        assertThat(viewModel.stateMutableLiveData.value).isEqualTo(MainViewModel.ViewState(
            isLoading = false,
            isError = true,
            currencyList = listOf()
        ))
    }

    @Test
    fun `verify state when FetchExchangeRateUseCase returns list success`() {

        every { appPreference.shouldRefreshData } returns true
        every { appPreference.isDataEmpty } returns true
        // given
        coEvery { mockService.getLatestExchangeRates(any()) } returns DataModels.getExchangeRateModel()
        coEvery { fetchExchangeRatesUseCase.execute() } returns FetchExchangeRatesUseCase.Result.Success(DataModels.getExchangeRateModel().toDomainModelList())

        // when
        runTest {
            viewModel.fetchData()
        }

        assertThat(viewModel.stateMutableLiveData.value).isEqualTo(MainViewModel.ViewState(
            isLoading = false,
            isError = false,
            currencyList = DataModels.getExchangeRateModel().toDomainModelList()
        ))
    }

    //TODO there is an unidentified issue happening with this UT. Will work on it later for my own.
    /*@Test
    fun `convert entered currency to supported currencies returns success`() {

        coEvery{ currencyDao.getAllCurrencies()} returns listOf(DataModels.getCurrencyEntity())
        //given
        coEvery {
            getConvertedExchangeRatesUseCase.execute(Currency("USD", 1.0), "10")
        } returns GetConvertedExchangeRatesUseCase.Result.Success(
            DataModels.getExchangeRateModel().toDomainModelList()
        )

        // when
        runTest {
            viewModel.convertEnteredCurrencyToSupportedCurrencies(Currency("USD", 1.0), "10")
        }

        assertThat(viewModel.stateMutableLiveData.value).isEqualTo(MainViewModel.ViewState(
            isLoading = false,
            isError = false,
            currencyList = DataModels.getExchangeRateModel().toDomainModelList()
        ))
    }*/

    @AfterEach
    fun tearDown(){
        Dispatchers.resetMain()
    }
}