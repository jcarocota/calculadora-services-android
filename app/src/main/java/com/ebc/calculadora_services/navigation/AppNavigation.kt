package com.ebc.calculadora_services.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.ebc.calculadora_services.navigation.screen.HomeScreen
import com.ebc.calculadora_services.navigation.screen.ThankyouScreen
import com.ebc.calculadora_services.viewmodel.TipViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val tipViewModel: TipViewModel = viewModel()

    NavHost (
        navController = navController,
        startDestination = "main",
    ) {
        composable(route ="main") {
            HomeScreen(navController = navController, viewModel = tipViewModel)
        }
        composable(route ="thankyou") {
            ThankyouScreen(navController = navController, viewModel = tipViewModel)
        }
    }
}