package com.lucky.allofthem.di

import com.google.gson.GsonBuilder
import com.lucky.allofthem.BuildConfig
import com.lucky.allofthem.common.constant.CommonConstant
import com.lucky.allofthem.common.constant.DomainConstant
import com.lucky.allofthem.data.remote.ApiInterceptor
import com.lucky.allofthem.data.remote.api.WeatherApi
import com.lucky.allofthem.data.remote.datasource.WeatherDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideWeatherDatasource(
        weatherApi: WeatherApi
    ): WeatherDatasource {
        return WeatherDatasource(weatherApi)
    }


    @Provides
    @Singleton
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val builder = GsonBuilder().disableHtmlEscaping().create()
        return Retrofit.Builder()
            .baseUrl(DomainConstant.WEATHER_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(builder))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cookieJar: JavaNetCookieJar,
        interceptor: Interceptor,
        logInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CommonConstant.TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(CommonConstant.TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS)
            .readTimeout(CommonConstant.TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .addInterceptor(interceptor)
            .addInterceptor(logInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideCookieJar(): JavaNetCookieJar {
        return JavaNetCookieJar(CookieManager())
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return ApiInterceptor()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }

}