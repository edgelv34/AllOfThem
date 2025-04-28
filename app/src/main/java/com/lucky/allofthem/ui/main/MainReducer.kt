package com.lucky.allofthem.ui.main

import com.lucky.allofthem.ui.mvi.Reducer

class MainReducer: Reducer<MainState, MainEvent, MainEffect>() {

    override fun reduce(state: MainState, event: MainEvent, sideEffect: (MainEffect) -> Unit): MainState {
        return when(event) {
            is MainEvent.GetShortTermForecast -> state.copy(
                isLoading = true,
                error = ""
            )
            is MainEvent.GetShortTermForecastSuccess -> state.copy(
                isLoading = false,
                place = state.place + event.places,
                error = ""
            )

            is MainEvent.UpdateLocation -> state.copy(
                location = event.location
            )

            is MainEvent.Failed -> state.copy(
                isLoading = false,
                error = event.error
            )
        }
    }
}