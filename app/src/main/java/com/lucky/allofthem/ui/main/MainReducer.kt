package com.lucky.allofthem.ui.main

import com.lucky.allofthem.ui.mvi.Reducer

class MainReducer: Reducer<MainState, MainEvent, MainEffect>() {

    override fun reduce(state: MainState, event: MainEvent, sideEffect: (MainEffect) -> Unit): MainState {
        return when(event) {
            MainEvent.GetPlace -> {
                state.copy(place = emptyList(), isLoading = true)
            }
        }
    }
}