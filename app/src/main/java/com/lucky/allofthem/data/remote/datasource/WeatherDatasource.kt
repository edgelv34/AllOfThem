package com.lucky.allofthem.data.remote.datasource

import android.util.Log
import com.lucky.allofthem.common.convertGridFromGPS
import com.lucky.allofthem.data.remote.api.WeatherApi
import com.lucky.allofthem.data.remote.dto.WeatherForecastDto

class WeatherDatasource(
    private val weatherApi: WeatherApi
) {
    suspend fun getShortTermForecast(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        lat: Double,
        lng: Double,
        dataType: String = "JSON"
    ): WeatherForecastDto {
        val latXLngY = convertGridFromGPS(lat = lat, lng = lng)
        Log.d("@@@", "enterd3")
        return weatherApi.getShortTermForecast(
            serviceKey = serviceKey,
            numOfRows = numOfRows,
            pageNo = pageNo,
            baseDate = baseDate,
            baseTime = baseTime,
            nx = latXLngY.x?.toInt() ?: -1,
            ny = latXLngY.y?.toInt() ?: -1,
            dataType = dataType,
        )
    }

}