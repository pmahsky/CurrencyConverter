package com.app.currency_converter.data.database

import androidx.room.*
import com.app.currency_converter.data.database.model.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrency(currency: CurrencyEntity)

    @Transaction
    suspend fun upsertCurrencies(currencies: List<CurrencyEntity>) {
        currencies.forEach { upsertCurrency(it) }
    }

    @Transaction
    suspend fun updateCurrencyList(currencies: List<CurrencyEntity>) {
        currencies.forEach { updateExchangeRate(it.currencyCode, it.exchangeRate) }
    }

    @Query("UPDATE currency_table SET column_exchangeRate = :exchangeRate WHERE column_currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Query("SELECT * FROM currency_table WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): CurrencyEntity

    @Query("SELECT * FROM currency_table ORDER BY column_currencyCode ASC")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currency_table WHERE column_isSelected = 1 ORDER BY column_order ASC")
    suspend fun getSelectedCurrencies(): List<CurrencyEntity>
}
