package com.app.currency_converter.data.network.service

import com.app.currency_converter.data.network.model.ExchangeRate
import com.app.currency_converter.data.network.response.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {
    @GET("/latest.json")
    suspend fun getLatestExchangeRates(
        @Query("app_id") appId: String
    ): ExchangeRate?
}