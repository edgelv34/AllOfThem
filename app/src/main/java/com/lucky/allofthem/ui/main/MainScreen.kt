package com.lucky.allofthem.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lucky.allofthem.BuildConfig
import com.lucky.allofthem.ui.theme.AllOfThemTheme

@Composable
fun MainScreen(innerPadding: PaddingValues) {

    Row(
        modifier = Modifier.padding(innerPadding)
    ) {
        Greeting(name = BuildConfig.MAPS_API_KEY)
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AllOfThemTheme {
        Greeting("Android")
    }
}