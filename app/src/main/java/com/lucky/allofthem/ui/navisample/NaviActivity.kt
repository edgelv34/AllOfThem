package com.lucky.allofthem.ui.navisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucky.allofthem.ui.navigation.NaviRouter
import com.lucky.allofthem.ui.theme.AllOfThemTheme

class NaviActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllOfThemTheme {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NaviRouter.AScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<NaviRouter.AScreen> {
                            AScreen {
                                navController.navigate(NaviRouter.BScreen)
                            }
                        }

                        composable<NaviRouter.BScreen> {
                            BScreen {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}