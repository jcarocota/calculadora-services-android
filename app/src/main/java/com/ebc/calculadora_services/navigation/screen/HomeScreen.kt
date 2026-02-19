package com.ebc.calculadora_services.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ebc.calculadora_services.R
import com.ebc.calculadora_services.components.TipPercentageSelect
import com.ebc.calculadora_services.viewmodel.TipViewModel

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: TipViewModel = viewModel()
) {
    val tipOptions = listOf(5, 10, 15, 20)

    val billAmount by viewModel.billAmount.collectAsState()
    val tipPercent by viewModel.tipPercent.collectAsState()
    val customTipAmount by viewModel.customTipAmount.collectAsState()
    val total by viewModel.totalToPay.collectAsState()
    val loadingRandomTip by viewModel.loadingRandomTip.collectAsState()

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.bill)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = "Total de la cuenta")
        TextField(
            value = billAmount,
            onValueChange = {viewModel.setBillAmount(it)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Selecciona un porcentaje de propina")
        TipPercentageSelect(
            options = tipOptions,
            selected = tipPercent,
            onSelected = viewModel::setTipPercent,
            enabled = true //TODO: completar esta parte
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "¿propina persolanizada?")

            Button(
                onClick = viewModel::getRandomTip
            ) {
                if(loadingRandomTip) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = if (loadingRandomTip) "Consultando..." else "random!!!"
                )
            }
        }


        TextField(
            value = customTipAmount,
            onValueChange = viewModel::setCustomTipAmount,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Total a pagar $$total", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = viewModel::reset
                ) {
                    Text("Reiniciar")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {navController.navigate("thankyou")}
                ) {
                    Text("Pagar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.width(300.dp)

                )
            }

        }
    }
}