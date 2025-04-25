package com.lucky.allofthem.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lucky.allofthem.domain.usecase.GetShortTermForecastUseCase
import com.lucky.allofthem.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getShortTermForecastUseCase: GetShortTermForecastUseCase
): MviViewModel<MainState, MainEvent, MainEffect>(
    initState = MainState(),
    reducer = MainReducer()
) {

    init {
        sendEvent(MainEvent.GetShortTermForecast(1))
    }


    override fun sendEvent(event: MainEvent) {
        super.sendEvent(event)
        when(event) {
            is MainEvent.GetShortTermForecast -> {
                getWeather(event.pageNo)
            }

            is MainEvent.GetShortTermForecastSuccess -> {

            }

            is MainEvent.Failed -> {

            }

        }

    }

    private fun getWeather(pageNo: Int) {
        viewModelScope.launch {
            getShortTermForecastUseCase.invoke(
                numOfRows = 10,
                pageNo = pageNo,
                baseDate = "20250425",
                baseTime = "0630",
                lat = 35.5873138888888,
                lng = 126.679608333333
            ).collect {
                Log.d("@@@", "asdasdasdasasdasd")
            }
        }
    }

}