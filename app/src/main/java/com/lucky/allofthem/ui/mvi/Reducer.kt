package com.lucky.allofthem.ui.mvi

abstract class Reducer<S: UiState, E: UiEvent, F: SideEffect> {
    abstract fun reduce(state: S, event: E, sideEffect: (F) -> Unit): S
}