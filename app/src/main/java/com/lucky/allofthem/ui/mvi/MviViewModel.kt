package com.lucky.allofthem.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * MVI 구조
 * ViewModel 에서 상속받아서 MVI (EVENT, STATE, EFFECT) 를 수행할 수 있도록 함.
 */
abstract class MviViewModel<S: UiState, E: UiEvent, F: SideEffect>(
    initState: S,
    private val reducer: Reducer<S, E, F>
): ViewModel() {

    private val eventChannel = Channel<E>(capacity = Channel.BUFFERED)

    private val _sideEffect: Channel<F> = Channel(capacity = Channel.BUFFERED)
    val sideEffect: Flow<F> get() = _sideEffect.receiveAsFlow()

    val uiState: StateFlow<S> = eventChannel
        .receiveAsFlow()
        .runningFold(initState, ::reduce)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initState
        )

    private fun reduce(uiState: S, uiEvent: E): S {
        return reducer.reduce(state = uiState, event = uiEvent) {
            sendSideEffect(it)
        }
    }

    fun sendSideEffect(effect: F) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }

    open fun sendEvent(event: E) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}