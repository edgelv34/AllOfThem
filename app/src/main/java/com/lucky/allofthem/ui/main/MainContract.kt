package com.lucky.allofthem.ui.main

import com.lucky.allofthem.domain.model.Location
import com.lucky.allofthem.domain.model.Place
import com.lucky.allofthem.domain.model.WeatherForecast
import com.lucky.allofthem.ui.mvi.SideEffect
import com.lucky.allofthem.ui.mvi.UiEvent
import com.lucky.allofthem.ui.mvi.UiState

data class MainState(
    val place: List<Place> = emptyList(),
    val pageNo: Int = 1,
    val isLoading: Boolean = false,
    val location: Location = Location(
        latitude = 35.5873138888888,
        longitude = 126.679608333333,
        timestamp = 0
    ),
    val weatherForecast: List<WeatherForecast> = emptyList()
): UiState

sealed class MainEvent: UiEvent {
    data class GetShortTermForecast(val pageNo: Int): MainEvent()
    data class UpdateShortTermForecast(val weatherForecast: List<WeatherForecast>) : MainEvent()
    data class UpdateLocation(val location: Location): MainEvent()
    data class Failed(val error: String) : MainEvent()
}

sealed class MainEffect: SideEffect {
    data class ShowToast(val message: String) : MainEffect()
}
