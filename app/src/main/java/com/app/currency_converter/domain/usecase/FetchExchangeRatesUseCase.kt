package com.app.currency_converter.domain.usecase

import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.domain.repository.ExchangeRateRepository
import timber.log.Timber
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

internal class FetchExchangeRatesUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend fun execute(): Result {
        return try {
            Timber.i("Calling fetchExchangeRates() in repository to get data  ***")
            exchangeRateRepository.fetchExchangeRates()?.let {
                Result.Success(it)
            } ?: Result.Error(RuntimeException())
        } catch (e: IOException) {
            Timber.i("Returning Error Result ***")
            Result.Error(e)
        }
    }

    sealed interface Result {
        data class Success(val data: List<Currency>) : Result
        data class Error(val e: Throwable) : Result
    }
}