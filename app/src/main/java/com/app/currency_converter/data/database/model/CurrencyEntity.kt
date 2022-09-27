package com.app.currency_converter.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.currency_converter.domain.domain_model.Currency

@Entity(tableName = "currency_table")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "column_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "column_exchangeRate")
    val exchangeRate: Double
)

internal fun CurrencyEntity.toDomainModel() = Currency(
    currencyCode = currencyCode,
    exchangeRate = exchangeRate
)