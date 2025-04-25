package com.lucky.allofthem.domain.repository

import com.lucky.allofthem.common.ApiResponse
import com.lucky.allofthem.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getShortTermForecast(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        lat: Double,
        lng: Double,
    ): Flow<ApiResponse<List<WeatherForecast>>>
}