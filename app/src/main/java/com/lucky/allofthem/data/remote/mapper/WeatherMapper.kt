package com.lucky.allofthem.data.remote.mapper

import com.lucky.allofthem.data.remote.dto.WeatherForecastDto
import com.lucky.allofthem.domain.model.WeatherForecast

fun WeatherForecastDto.mapper(): List<WeatherForecast> {
    return this.response.body.items.item.map { itemDto ->
        WeatherForecast(
            baseDate = itemDto.baseDate,
            baseTime = itemDto.baseTime,
            category = itemDto.category,
            fcstDate = itemDto.fcstDate,
            fcstTime = itemDto.fcstTime,
            fcstValue = itemDto.fcstValue,
            nx = itemDto.nx,
            ny = itemDto.ny
        )
    }
}