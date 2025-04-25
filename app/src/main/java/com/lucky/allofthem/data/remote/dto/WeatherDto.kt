package com.lucky.allofthem.data.remote.dto

data class WeatherForecastDto(
    val response: ResponseDto
)

data class ResponseDto(
    val body: BodyDto,
    val header: HeaderDto
)

data class BodyDto(
    val dataType: String,
    val items: ItemsDto,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class ItemsDto(
    val item: List<WeatherForecastItemDto>
)

data class HeaderDto(
    val resultCode: String,
    val resultMsg: String
)

/**
 * baseDate : 예보 발표일자
 * baseTime : 예보 발표시간
 * fcstDate : 예측 일자
 * fcstTime : 예측 시간
 * fcstValue : 예보 값
 * nx : 예보지점 X 좌표 (위도, 경도를 기상청에서 정한 좌표계로 변환된 X 좌표)
 * ny : 예보지점 Y 좌표 (위도, 경도를 기상청에서 정한 좌표계로 변환된 Y 좌표)
 */
data class WeatherForecastItemDto(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String,
    val nx: Int,
    val ny: Int
)
