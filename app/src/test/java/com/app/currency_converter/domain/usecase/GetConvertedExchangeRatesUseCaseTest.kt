package com.app.currency_converter.domain.usecase

import com.app.currency_converter.data.DataModels
import com.app.currency_converter.data.network.model.toDomainModelList
import com.app.currency_converter.domain.model.Currency
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class GetConvertedExchangeRatesUseCaseTest {
    private var useCase: GetConvertedExchangeRatesUseCase =
        mockk(relaxed = true)

    @Test
    fun `get converted currency list returns success`() {
        coEvery {
            useCase.execute(Currency("USD", 1.0), "10")
        } returns GetConvertedExchangeRatesUseCase.Result.Success(
            DataModels.getExchangeRateModel().toDomainModelList()
        )

        val result = runBlocking { useCase.execute(Currency("USD", 1.0), "10") }
        assertThat(result).isEqualTo(
            GetConvertedExchangeRatesUseCase.Result.Success(
                DataModels.getExchangeRateModel().toDomainModelList()
            )
        )
    }
}