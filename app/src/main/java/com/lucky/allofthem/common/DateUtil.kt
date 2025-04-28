package com.lucky.allofthem.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object DateUtil {


    /**
     * 날짜 포맷터 (yyyyMMdd)
     * 한시간 전의 날짜를 받아옴.
     */
    fun getBeforeOneHourLocalDate(): String {
        val now = LocalDateTime.now().minusHours(1)
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return now.format(dateFormatter)
    }

    /**
     * 시간 포맷터 (HH:mm)
     * 한시간 전의 시간을 받아옴.
     */
    fun getBeforeOneHourLocalTime(): String {
        val now = LocalDateTime.now().minusHours(1)
        val timeFormatter = DateTimeFormatter.ofPattern("HHmm")
        return now.format(timeFormatter)
    }
}