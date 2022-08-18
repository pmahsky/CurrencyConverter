package com.app.currency_converter.domain.di

import com.app.currency_converter.data.AppPreference
import com.app.currency_converter.data.database.CurrencyDao
import com.app.currency_converter.data.network.service.ApiService
import com.app.currency_converter.domain.repository.ExchangeRateRepository
import com.app.currency_converter.data.repositoryImpl.ExchangeRateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    internal fun provideRepository(
        apiService: ApiService,
        currencyDao: CurrencyDao,
        appPreference: AppPreference): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(apiService, currencyDao, appPreference)
    }
}