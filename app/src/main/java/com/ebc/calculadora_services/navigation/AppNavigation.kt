package com.ebc.calculadora_services.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.ebc.calculadora_services.navigation.screen.HomeScreen
import com.ebc.calculadora_services.navigation.screen.ThankyouScreen
import com.ebc.calculadora_services.viewmodel.TipViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val tipViewModel: TipViewModel = viewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "main"

    fun go(route: String) {
        scope.launch { drawerState.close() }
        if (currentRoute != route) {
            navController.navigate(route) {
                // evita apilar pantallas repetidas al usar drawer
                launchSingleTop = true
                restoreState = true
                popUpTo("main") { saveState = true }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Text(
                    text = "Menú",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = {Text("Home/Propinas")},
                    selected = currentRoute == "main",
                    onClick = { go("main") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = {Text("Thank you")},
                    selected = currentRoute == "thankyou",
                    onClick = { go("thankyou") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            when (currentRoute) {
                                "main" -> "Propinas"
                                "thankyou" -> "Thank you"
                                "lottery" -> "Lottery"
                                else -> "App"
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu, contentDescription = "Menú"
                            )
                        }
                    }
                )
            }
        ) {
            paddingValues ->
            NavHost (
                navController = navController,
                startDestination = "main",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route ="main") {
                    HomeScreen(navController = navController, viewModel = tipViewModel)
                }
                composable(route ="thankyou") {
                    ThankyouScreen(navController = navController, viewModel = tipViewModel)
                }
            }
        }
    }


    /*NavHost (
        navController = navController,
        startDestination = "main",
    ) {
        composable(route ="main") {
            HomeScreen(navController = navController, viewModel = tipViewModel)
        }
        composable(route ="thankyou") {
            ThankyouScreen(navController = navController, viewModel = tipViewModel)
        }
    }*/
}