package com.ebc.calculadora_services.navigation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    //TODO: Incluir ViewModel
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = "Total de la cuenta")
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}