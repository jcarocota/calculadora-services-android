package com.ebc.calculadora_services.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun TipPercentageSelect(
    options: List<Int> = listOf(5, 99, -8, 100, 25),
    selected: Int? = 100,
    onSelected: (Int) -> Unit = {},
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement =
            Arrangement.spacedBy(space = 8.dp)
    ) {
        options.forEach {
            option ->

            val isSelected = (selected == option)

            FilterChip(
                selected = isSelected,
                onClick = { onSelected(option) },
                label = {Text(text = "$option")},
                leadingIcon = {
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Seleccionado",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                colors = FilterChipDefaults.filterChipColors(

                )
            )
        }
    }
}

/*@Preview
@Composable
private fun TipPercentageSelectPreview() {
    TipPercentageSelect(options = listOf(1,2,3), selected = 1)
}*/