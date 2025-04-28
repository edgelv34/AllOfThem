package com.lucky.allofthem.ui.main

import com.lucky.allofthem.domain.model.Place
import com.lucky.allofthem.ui.mvi.SideEffect
import com.lucky.allofthem.ui.mvi.UiEvent
import com.lucky.allofthem.ui.mvi.UiState

data class MainState(
    val place: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
): UiState

sealed class MainEvent: UiEvent {
    data class GetShortTermForecast(val pageNo: Int): MainEvent()
    data class GetShortTermForecastSuccess(val places: List<Place>) : MainEvent()
    data class Failed(val error: String) : MainEvent()
}

sealed class MainEffect: SideEffect {
    data class ShowToast(val message: String) : MainEffect()
}
