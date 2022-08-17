package com.app.currency_converter.domain.usecase

import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.util.CurrencyConversionUtility
import com.app.currency_converter.util.Utils.roundToFourDecimalPlaces
import timber.log.Timber
import java.io.IOException
import java.math.BigDecimal
import javax.inject.Inject

internal class GetConvertedExchangeRatesUseCase @Inject constructor(
    private val currencyDao: CurrencyDao
) {
    suspend fun execute(currency: Currency, enteredAmountTobeConverted: String): Result {
        return try {
            Timber.i("Calling getConvertedCurrencyList() ***")
            Result.Success(getConvertedCurrencyList(currency, enteredAmountTobeConverted))
        } catch (e: IOException) {
            Timber.i("Returning Error Result ***")
            Result.Error(e)
        }
    }

    sealed interface Result {
        data class Success(val data: List<Currency>) : Result
        data class Error(val e: Throwable) : Result
    }

    private suspend fun getConvertedCurrencyList(
        currency: Currency,
        enteredAmountTobeConverted: String
    ): List<Currency> {
        val convertedCurrencyList = arrayListOf<Currency>()
        val allCurrenciesList = currencyDao.getAllCurrencies()
        allCurrenciesList.forEach {
            val fromRate = currency.exchangeRate
            val toRate = it.exchangeRate

            val conversionValue = CurrencyConversionUtility.convertCurrency(
                BigDecimal(enteredAmountTobeConverted.replace(",", ".")),
                fromRate,
                toRate
            )
            it.conversion.conversionValue = conversionValue

            convertedCurrencyList.add(
                Currency(
                    it.currencyCode,
                    conversionValue.roundToFourDecimalPlaces().toDouble()
                )
            )
        }
        return convertedCurrencyList

    }
}