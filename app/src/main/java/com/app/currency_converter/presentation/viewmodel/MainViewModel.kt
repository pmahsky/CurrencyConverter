package com.app.currency_converter.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.domain.usecase.FetchExchangeRatesUseCase
import com.app.currency_converter.domain.usecase.GetConvertedExchangeRatesUseCase
import com.app.currency_converter.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val fetchExchangeRatesUseCase: FetchExchangeRatesUseCase,
    private val getConvertedExchangeRatesUseCase: GetConvertedExchangeRatesUseCase
) : BaseViewModel() {

    override fun onFetchData() {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        Timber.i("Starting to fetch data ***")
        viewModelScope.launch {
            fetchExchangeRatesUseCase.execute().also { result ->
                val action = when (result) {
                    is FetchExchangeRatesUseCase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Timber.i("Result Success but data is empty ***")
                            Action.ExchangeRatesLoadingFailure
                        } else {
                            Timber.i("Result Success data received ***")
                            Action.ExchangeRatesLoadingSuccess(result.data)
                        }

                    is FetchExchangeRatesUseCase.Result.Error -> {
                        Timber.i("Result Failure ***")
                        Action.ExchangeRatesLoadingFailure
                    }
                }
                updateUiStateBasedOnAction(
                    viewAction = action,
                    shouldUpdateConvertedCurrencyList = false
                )
            }
        }
    }

    fun convertEnteredCurrencyToSupportedCurrencies(
        currency: Currency,
        enteredAmountTobeConverted: String
    ) {
        Timber.i("Starting to convert currency for Currency: ${currency.currencyCode} with rate: ${currency.exchangeRate} for amount: ${enteredAmountTobeConverted} ***")
        viewModelScope.launch {
            getConvertedExchangeRatesUseCase.execute(
                currency = currency,
                enteredAmountTobeConverted = enteredAmountTobeConverted
            )
                .also { result ->
                    val action = when (result) {
                        is GetConvertedExchangeRatesUseCase.Result.Success ->
                            if (result.data.isEmpty()) {
                                Timber.i("Result Success but data is empty ***")
                                Action.ExchangeRatesLoadingFailure
                            } else {
                                Timber.i("Result Success data received ***")
                                Action.ExchangeRatesLoadingSuccess(result.data)
                            }

                        is GetConvertedExchangeRatesUseCase.Result.Error -> {
                            Timber.i("Result Failure ***")
                            Action.ExchangeRatesLoadingFailure
                        }
                    }
                    updateUiStateBasedOnAction(
                        viewAction = action,
                        shouldUpdateConvertedCurrencyList = true
                    )
                }
        }
    }

    private fun updateUiStateBasedOnAction(
        viewAction: Action,
        shouldUpdateConvertedCurrencyList: Boolean
    ) {
        Timber.i("viewAction: $viewAction  and shouldUpdateConvertedCurrencyList: $shouldUpdateConvertedCurrencyList ***")
        val viewState = when (viewAction) {
            is Action.ExchangeRatesLoadingSuccess -> ViewState(
                isLoading = false,
                isError = false,
                shouldUpdateConvertedCurrencyList = shouldUpdateConvertedCurrencyList,
                currencyList = viewAction.currencyList
            )
            is Action.ExchangeRatesLoadingFailure -> ViewState(
                isLoading = false,
                isError = true,
                shouldUpdateConvertedCurrencyList = shouldUpdateConvertedCurrencyList,
                currencyList = listOf()
            )
        }
        stateMutableLiveData.postValue(viewState)
    }

    fun processInputAmount(inputAmount: String): Boolean {
        if(inputAmount.isNotEmpty()) {
            val cleanedInputAmount = cleanInput(inputAmount)
            return isInputAmountValid(cleanedInputAmount)
        }
        return false
    }

    /**
     * If input has a leading decimal separator it prefixes it with a zero.
     * If input has a leading 0 it removes it.
     * Examples:   "." -> "0."
     *            "00" -> "0"
     *            "07" -> "0"
     */
    private fun cleanInput(input: String): String {
        var cleanInput = input.replace(",", ".")
        when {
            "." == cleanInput -> cleanInput = "0."
            Regex("0[^.]").matches(cleanInput) -> cleanInput =
                input[Utils.Order.SECOND.position].toString()
        }
        return cleanInput
    }

    private fun isInputAmountValid(input: String): Boolean {
        return (validateLength(input) &&
                validateDecimalPlaces(input) &&
                validateDecimalSeparator(input))
    }

    /**
     * Assures the whole part of the input is not above 20 digits long.
     */
    private fun validateLength(input: String): Boolean {
        val maxDigitsAllowed = 20
        if (!input.contains(".") && input.length > maxDigitsAllowed) {
            return false
        }
        return true
    }

    /**
     * Assures the decimal part of the input is at most 4 digits.
     */
    private fun validateDecimalPlaces(input: String): Boolean {
        val maxDecimalPlacesAllowed = 4
        if (input.contains(".") &&
            input.substring(input.indexOf(".") + 1).length > maxDecimalPlacesAllowed
        ) {
            return false
        }
        return true
    }

    /**
     * Assures the input contains at most 1 decimal separator.
     */
    private fun validateDecimalSeparator(input: String): Boolean {
        val decimalSeparatorCount = input.asSequence()
            .count { it == '.' }
        if (decimalSeparatorCount > 1) {
            return false
        }
        return true
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val shouldUpdateConvertedCurrencyList: Boolean = false,
        val currencyList: List<Currency> = listOf(),
    )

    internal sealed interface Action {
        class ExchangeRatesLoadingSuccess(val currencyList: List<Currency>) : Action
        object ExchangeRatesLoadingFailure : Action
    }

}