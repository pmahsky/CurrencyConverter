package com.app.currency_converter.viewmodel

import androidx.lifecycle.viewModelScope
import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.domain.usecase.GetExchangeRatesUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getExchangeRatesUsecase: GetExchangeRatesUsecase
) : BaseViewModel() {

    override fun onFetchData() {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            getExchangeRatesUsecase.execute().also { result ->
                val action = when (result) {
                    is GetExchangeRatesUsecase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Timber.i("Result Success but data is empty ***")
                            Action.ExchangeRatesLoadingFailure
                        } else {
                            Timber.i("Result Success data received ***")
                            Action.ExchangeRatesLoadingSuccess(result.data)
                        }

                    is GetExchangeRatesUsecase.Result.Error -> {
                        Timber.i("Result Failure ***")
                        Action.ExchangeRatesLoadingFailure
                    }
                }
                updateUiStateBasedOnAction(action)
            }
        }
    }

    private fun updateUiStateBasedOnAction(viewAction: Action) {
        Timber.i("viewAction: $viewAction ***")
        val viewState = when (viewAction) {
            is Action.ExchangeRatesLoadingSuccess -> ViewState(
                isLoading = false,
                isError = false,
                currencyList = viewAction.currencyList
            )
            is Action.ExchangeRatesLoadingFailure -> ViewState(
                isLoading = false,
                isError = true,
                currencyList = listOf()
            )
        }
        stateMutableLiveData.postValue(viewState)
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val currencyList: List<Currency> = listOf()
    )

    internal sealed interface Action {
        class ExchangeRatesLoadingSuccess(val currencyList: List<Currency>) : Action
        object ExchangeRatesLoadingFailure : Action
    }

}