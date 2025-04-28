package com.lucky.allofthem.data.repository

import android.util.Log
import com.lucky.allofthem.common.ApiResponse
import com.lucky.allofthem.common.safeApiCall
import com.lucky.allofthem.data.remote.datasource.WeatherDatasource
import com.lucky.allofthem.data.remote.mapper.mapper
import com.lucky.allofthem.domain.model.WeatherForecast
import com.lucky.allofthem.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepositoryImpl(
    private val weatherDatasource: WeatherDatasource
): WeatherRepository {

    override suspend fun getShortTermForecast(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        lat: Double,
        lng: Double
    ): Flow<ApiResponse<List<WeatherForecast>>> = flow {
        val response = safeApiCall {
            val weatherResponse = weatherDatasource.getShortTermForecast(
                serviceKey = serviceKey,
                numOfRows = numOfRows,
                pageNo = pageNo,
                baseDate = baseDate,
                baseTime = baseTime,
                lat = lat,
                lng = lng
            )
            //공공데이터 포털에서 성공코드 == 00
            //실패하면 IllegalStateException 를 throw 하도록 함.
            if (weatherResponse.response.header.resultCode != "00") {
                throw IllegalStateException(weatherResponse.response.header.resultMsg)
            }
            weatherResponse.mapper()
        }
        emit(response)
    }.flowOn(Dispatchers.IO)
}