package com.lucky.allofthem.domain.usecase

import android.util.Log
import com.lucky.allofthem.BuildConfig
import com.lucky.allofthem.common.ApiResponse
import com.lucky.allofthem.domain.model.WeatherForecast
import com.lucky.allofthem.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShortTermForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        lat: Double,
        lng: Double
    ): Flow<ApiResponse<List<WeatherForecast>>> {
        Log.d("@@@", "enterd1")
        return weatherRepository.getShortTermForecast(
            serviceKey = BuildConfig.WEATHER_SERVICE_KEY,
            numOfRows = numOfRows,
            pageNo = pageNo,
            baseDate = baseDate,
            baseTime = baseTime,
            lat = lat,
            lng = lng
        )
    }

}