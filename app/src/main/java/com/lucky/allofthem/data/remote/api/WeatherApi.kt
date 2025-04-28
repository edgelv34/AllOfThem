package com.lucky.allofthem.data.remote.api

import com.lucky.allofthem.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val SHORT_TERM_FORECAST = "1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"
    }


    /**
     * https://www.data.go.kr/data/15084084/openapi.do#/tab_layer_detail_function
     * 공공기관 초단기 예보 API
     * 서비스키			ServiceKey	4	필수	-			공공데이터포털에서 받은 인증키
     * 페이지 번호			pageNo		4	필수	1			페이지번호
     * 한 페이지 결과 수	numOfRows	4	필수	1000		한 페이지 결과 수
     * 응답자료형식		dataType	4	옵션	XML			요청자료형식(XML/JSON) Default: XML
     * 발표일자			base_date	8	필수	20210628	‘21년 6월 28일 발표
     * 발표시각			base_time	4	필수	0630		06시30분 발표(30분 단위)
     * 예보지점 X 좌표		nx			2	필수	55			예보지점 X 좌표값
     * 예보지점 Y 좌표		ny			2	필수	127			예보지점 Y 좌표값
     */
    @GET(SHORT_TERM_FORECAST)
    suspend fun getShortTermForecast(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("dataType") dataType: String = "JSON"
    ): WeatherForecastDto
}