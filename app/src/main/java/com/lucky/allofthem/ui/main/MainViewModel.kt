package com.lucky.allofthem.ui.main

import com.lucky.allofthem.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): MviViewModel<MainState, MainEvent, MainEffect>(
    initState = MainState(),
    reducer = MainReducer()
) {

}