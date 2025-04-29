package com.lucky.allofthem.ui.main

import androidx.lifecycle.viewModelScope
import com.lucky.allofthem.common.ApiResponse
import com.lucky.allofthem.common.DateUtil
import com.lucky.allofthem.domain.usecase.GetShortTermForecastUseCase
import com.lucky.allofthem.domain.usecase.ObserveLocationUseCase
import com.lucky.allofthem.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getShortTermForecastUseCase: GetShortTermForecastUseCase,
    val observeLocationUseCase: ObserveLocationUseCase
): MviViewModel<MainState, MainEvent, MainEffect>(
    initState = MainState(),
    reducer = MainReducer()
) {

    init {
        observeLocation()
    }


    override fun sendEvent(event: MainEvent) {
        super.sendEvent(event)
        when(event) {
            is MainEvent.GetShortTermForecast -> {
                //기상청 초단기예보 정보 요청
                getWeather(event.pageNo)
            }

            is MainEvent.UpdateLocation -> {
                //위치정보가 업데이트되면 기상청 초단기예보 정보도 변경하기위해서 API 재 발송
                sendEvent(MainEvent.GetShortTermForecast(uiState.value.pageNo))
            }

            else -> {}
        }
    }

    private fun observeLocation() {
        viewModelScope.launch {
            observeLocationUseCase.invoke().collectLatest { location ->
                sendEvent(MainEvent.UpdateLocation(location))
            }
        }
    }

    private fun getWeather(pageNo: Int) {
        viewModelScope.launch {
            getShortTermForecastUseCase.invoke(
                numOfRows = 30,
                pageNo = pageNo,
                baseDate = DateUtil.getBeforeOneHourLocalDate(),
                baseTime = DateUtil.getBeforeOneHourLocalTime(),
                lat = uiState.value.location.latitude,
                lng =  uiState.value.location.longitude
            ).collectLatest { response ->
                when(response) {
                    is ApiResponse.Success -> {

                        sendEvent(MainEvent.UpdateShortTermForecast(weatherForecast = response.data))
                    }

                    is ApiResponse.Failure -> {
                        sendEvent(MainEvent.Failed(response.message))
                    }
                }
            }
        }
    }

}