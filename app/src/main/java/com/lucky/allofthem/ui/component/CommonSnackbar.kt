package com.lucky.allofthem.ui.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CommonSnackbar(
    coroutineScope: CoroutineScope,
    snackbarHost: SnackbarHostState,
    message: String,
    actionLabel: String = "",
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = SnackbarDuration.Short,
    onSnackbarResult: (SnackbarResult) -> Unit? = {}
) {

    showSnackBar(
        coroutineScope = coroutineScope,
        snackbarHost = snackbarHost,
        message = message,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration,
        onSnackbarResult = onSnackbarResult
    )

}


private fun showSnackBar(
    coroutineScope: CoroutineScope,
    snackbarHost: SnackbarHostState,
    message: String,
    actionLabel: String?,
    withDismissAction: Boolean,
    duration: SnackbarDuration,
    onSnackbarResult: (SnackbarResult) -> Unit?
) {
    coroutineScope.launch {
        val snackBar =
            snackbarHost.showSnackbar(
                message,
                actionLabel,
                withDismissAction,
                duration
            )

        when (snackBar) {
            SnackbarResult.ActionPerformed -> {
                onSnackbarResult(SnackbarResult.ActionPerformed)
            }

            SnackbarResult.Dismissed -> {
                onSnackbarResult(SnackbarResult.Dismissed)
            }
        }
    }
}