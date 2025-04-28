package com.lucky.allofthem.di

import com.lucky.allofthem.data.remote.datasource.WeatherDatasource
import com.lucky.allofthem.data.repository.LocationRepositoryImpl
import com.lucky.allofthem.data.repository.WeatherRepositoryImpl
import com.lucky.allofthem.domain.repository.LocationRepository
import com.lucky.allofthem.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDatasource: WeatherDatasource): WeatherRepository = WeatherRepositoryImpl(weatherDatasource)

    @Provides
    @Singleton
    fun provideLocationRepository(): LocationRepository = LocationRepositoryImpl()

}