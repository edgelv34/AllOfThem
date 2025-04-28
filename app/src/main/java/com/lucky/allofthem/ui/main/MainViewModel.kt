package com.lucky.allofthem.ui.main

import androidx.lifecycle.viewModelScope
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
                getWeather(event.pageNo)
            }

            is MainEvent.GetShortTermForecastSuccess -> {

            }

            is MainEvent.UpdateLocation -> {
                sendEvent(MainEvent.GetShortTermForecast(1))
            }

            is MainEvent.Failed -> {

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
                numOfRows = 10,
                pageNo = pageNo,
                baseDate = DateUtil.getBeforeOneHourLocalDate(),
                baseTime = DateUtil.getBeforeOneHourLocalTime(),
                lat = uiState.value.location.latitude,
                lng =  uiState.value.location.longitude
            ).collectLatest {

            }
        }
    }

}