package com.app.currency_converter.domain.repositoryImpl

import android.content.Context
import com.app.currency_converter.BuildConfig
import com.app.currency_converter.R
import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.database.model.CurrencyEntity
import com.app.currency_converter.data.network.response.ExchangeRateResponse
import com.app.currency_converter.data.network.response.ExchangeRates
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.domain.model.Result
import com.app.currency_converter.domain.repository.ExchangeRateRepository
import com.app.currency_converter.util.Utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao,
    private val appPreference: AppPreference
) : ExchangeRateRepository {
    override var isFirstLaunch = appPreference.isFirstLaunch
    override val timestampInSeconds = appPreference.timestampInSeconds

    override fun getAllCurrencies() = currencyDao.getAllCurrencies()

    override fun getSelectedCurrencies() = currencyDao.getSelectedCurrencies()

    override fun upsertCurrency(currency: CurrencyEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertCurrency(currency)
        }
    }

    override fun upsertCurrencies(currencies: List<CurrencyEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertCurrencies(currencies)
        }
    }

    override suspend fun getCurrency(currencyCode: String) = currencyDao.getCurrency(currencyCode)

    override suspend fun fetchCurrencies(): Result {
        if (context.isNetworkAvailable() && (appPreference.isDataEmpty || appPreference.isDataStale)) {
            val retrofitResponse: Response<ExchangeRateResponse>
            try {
                retrofitResponse = apiService.getLatestExchangeRates(appId = BuildConfig.OPEN_EXCHANGE_API_KEY)
            } catch (e: SocketTimeoutException) {
                return Result.Error(context.getString(R.string.error_message_network_time_out))
            }
            return if (retrofitResponse.isSuccessful) {
                initSaveCurrenciesInLocalDatabase(retrofitResponse)
                Result.Success
            } else {
                Result.Error(retrofitResponse.errorBody()?.string())
            }
        } else if (!appPreference.isDataEmpty) {
            return Result.Success
        } else {
            return Result.Error(context.getString(R.string.error_message_network_not_available))
        }
    }

    private suspend fun initSaveCurrenciesInLocalDatabase(retrofitResponse: Response<ExchangeRateResponse>) {
        retrofitResponse.body()?.let { responseBody->
            responseBody.exchangeRates?.let {
                    saveCurrenciesInLocalDatabase(it)
            }
        }
    }

    private suspend fun saveCurrenciesInLocalDatabase(exchangeRates: ExchangeRates) {
        when {
            appPreference.isDataEmpty -> currencyDao.upsertCurrencies(exchangeRates.currencies)
            appPreference.isDataStale -> currencyDao.updateExchangeRates(exchangeRates.currencies)
        }
    }
}