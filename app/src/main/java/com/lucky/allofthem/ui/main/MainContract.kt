package com.lucky.allofthem.ui.main

import com.lucky.allofthem.common.AppError
import com.lucky.allofthem.domain.model.Place
import com.lucky.allofthem.ui.mvi.SideEffect
import com.lucky.allofthem.ui.mvi.UiEvent
import com.lucky.allofthem.ui.mvi.UiState

data class MainState(
    val place: List<Place> = emptyList(),
    val isLoading: Boolean = false
): UiState

sealed class MainEvent: UiEvent {
    object LoadInitial : MainEvent()
    data class LoadSuccess(val places: List<Place>) : MainEvent()
    data class LoadFailed(val error: AppError) : MainEvent()
}

sealed class MainEffect: SideEffect {
    data class ShowToast(val message: String) : MainEffect()
}
