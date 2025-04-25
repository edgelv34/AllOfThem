package com.lucky.allofthem

import com.lucky.allofthem.common.LatXLngY
import com.lucky.allofthem.common.convertGridFromGPS
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun `gps 위도와 경도를 기상청에서 조회하기위한 좌표계로 변환`() {
        val latXLngY = convertGridFromGPS(lat = 35.5873138888888, lng = 126.679608333333)
        assertEquals(
            LatXLngY(lat=35.5873138888888, lng=126.679608333333, x=55.0, y=84.0),
            latXLngY
        )

    }
}