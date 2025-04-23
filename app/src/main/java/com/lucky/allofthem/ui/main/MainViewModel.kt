package com.lucky.allofthem.ui.main

import androidx.lifecycle.viewModelScope
import com.lucky.allofthem.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): MviViewModel<MainState, MainEvent, MainEffect>(
    initState = MainState(),
    reducer = MainReducer()
) {

    init {
        sendEvent(MainEvent.LoadInitial)
    }


    override fun sendEvent(event: MainEvent) {
        super.sendEvent(event)
        when(event) {
            MainEvent.LoadInitial -> {
                viewModelScope.launch {
                    delay(1500)
                    sendEvent(MainEvent.LoadSuccess(places = emptyList()))
                }
            }

            is MainEvent.LoadSuccess -> {

            }

            is MainEvent.LoadFailed -> {}

        }

    }

    public fun getPlace() {
        sendEvent(MainEvent.LoadInitial)


    }

}