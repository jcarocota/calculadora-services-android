package com.ebc.calculadora_services.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.ebc.calculadora_services.navigation.screen.HomeScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost (
        navController = navController,
        startDestination = "main",
    ) {
        composable(route ="main") {
            HomeScreen(navController = navController)
        }
        composable(route ="thankyou") {
            Text("Salida")
            Button(
                onClick = { navController.navigate(route = "main") }
            ) { Text("Ir a main") }
        }
    }
}