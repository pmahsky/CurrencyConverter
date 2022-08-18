package com.app.currency_converter.data.repositoryImpl

import com.app.currency_converter.BuildConfig
import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.database.model.CurrencyEntity
import com.app.currency_converter.data.database.model.toDomainModel
import com.app.currency_converter.data.network.model.toDomainModelList
import com.app.currency_converter.data.network.model.toEntityList
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.domain.repository.ExchangeRateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

internal class ExchangeRateRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao,
    private val appPreference: AppPreference
) : ExchangeRateRepository {
    override val timestampInSeconds = appPreference.timestampInSeconds

    override suspend fun getAllCurrencies() = currencyDao.getAllCurrencies()

    override suspend fun upsertCurrency(currency: CurrencyEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertCurrency(currency)
        }
    }

    override suspend fun upsertCurrencies(currencies: List<CurrencyEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertCurrencies(currencies)
        }
    }

    override suspend fun fetchExchangeRates(): List<Currency>? {
        return try {
            Timber.i("start of data fetch ***")
            if ((appPreference.isDataEmpty || appPreference.shouldRefreshData)) {
                Timber.i("appPreference.isDataEmpty: ${appPreference.isDataEmpty} || appPreference.shouldRefreshData: ${appPreference.shouldRefreshData} IF***")
                Timber.i("Getting fresh data from Api ***")
                val exchangeRatesResponse =
                    apiService.getLatestExchangeRates(appId = BuildConfig.OPEN_EXCHANGE_API_KEY)

                exchangeRatesResponse?.let {
                    val currencyEntityList = it.toEntityList()
                    addUpdateCurrenciesInLocalDatabase(currencyEntityList = currencyEntityList)
                    appPreference.timestampInSeconds = exchangeRatesResponse.timestamp ?: 0
                    it.toDomainModelList()
                }
            } else {
                Timber.i("appPreference.isDataEmpty: ${appPreference.isDataEmpty} || appPreference.shouldRefreshData: ${appPreference.shouldRefreshData} ELSE IF***")
                Timber.i("Returning data from DB ***")
                currencyDao.getAllCurrencies()
                    ?.map { it.toDomainModel() }
            }
        } catch (e: UnknownHostException) {
            Timber.i("Exception occurred, returning data from DB if available ${e.printStackTrace()} ***")
            currencyDao.getAllCurrencies()
                ?.map { it.toDomainModel() }
        }
    }

    private suspend fun addUpdateCurrenciesInLocalDatabase(currencyEntityList: List<CurrencyEntity>) {
        Timber.i("Adding/Updating data in DB ***")
        when {
            appPreference.isDataEmpty -> currencyDao.upsertCurrencies(currencyEntityList)
            appPreference.shouldRefreshData -> currencyDao.updateCurrencyList(currencyEntityList)
        }
    }
}