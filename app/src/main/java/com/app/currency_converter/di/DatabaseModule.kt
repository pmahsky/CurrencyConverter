package com.app.currency_converter.di

import android.content.Context
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.database.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return CurrencyDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }
}