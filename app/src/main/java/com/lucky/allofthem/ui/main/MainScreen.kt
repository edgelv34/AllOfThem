package com.lucky.allofthem.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lucky.allofthem.ui.component.CommonSnackbar
import com.lucky.allofthem.ui.component.Loading
import com.lucky.allofthem.ui.theme.AllOfThemTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val snackBarHost = remember { SnackbarHostState() }
    var showSnackBar by rememberSaveable { mutableStateOf("") }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        }
    ) { innerPadding ->

        Row(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }

    if (state.isLoading) {
        Loading()
    }

    //스낵바
    if (showSnackBar.isNotBlank()) {
        snackBarHost.currentSnackbarData?.dismiss()
        CommonSnackbar(
            snackbarHost = snackBarHost,
            coroutineScope = coroutineScope,
            message = showSnackBar
        )
        showSnackBar = ""
    }
}







@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AllOfThemTheme {

    }
}