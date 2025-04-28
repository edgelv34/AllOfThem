package com.lucky.allofthem.ui.main

import com.lucky.allofthem.ui.mvi.Reducer

class MainReducer: Reducer<MainState, MainEvent, MainEffect>() {

    override fun reduce(state: MainState, event: MainEvent, sideEffect: (MainEffect) -> Unit): MainState {
        return when(event) {
            is MainEvent.GetShortTermForecast -> state.copy(
                isLoading = true
            )
            is MainEvent.UpdateShortTermForecast -> state.copy(
                isLoading = false,
                weatherForecast = event.weatherForecast
            )

            is MainEvent.UpdateLocation -> state.copy(
                location = event.location
            )

            is MainEvent.Failed -> {
                sideEffect(MainEffect.ShowToast(event.error))
                state.copy(
                    isLoading = false
                )
            }


        }
    }
}