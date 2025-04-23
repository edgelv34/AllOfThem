package com.lucky.allofthem.ui.main

import com.lucky.allofthem.ui.mvi.Reducer

class MainReducer: Reducer<MainState, MainEvent, MainEffect>() {

    override fun reduce(state: MainState, event: MainEvent, sideEffect: (MainEffect) -> Unit): MainState {
        return when(event) {
            MainEvent.LoadInitial -> state.copy(isLoading = true)
            is MainEvent.LoadSuccess -> state.copy(
                isLoading = false,
                place = state.place + event.places
            )
            is MainEvent.LoadFailed -> state.copy(
                isLoading = false
            )
        }
    }
}