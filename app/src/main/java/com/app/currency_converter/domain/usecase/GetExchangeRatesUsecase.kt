package com.app.currency_converter.domain.usecase

import com.app.currency_converter.domain.repository.ExchangeRateRepository
import java.io.IOException
import javax.inject.Inject

class GetExchangeRatesUsecase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend fun execute(): com.app.currency_converter.domain.model.Result {
        return try {
            exchangeRateRepository.fetchCurrencies()
        } catch (e: IOException) {
            com.app.currency_converter.domain.model.Result.Error(e.message)
        }
    }
}