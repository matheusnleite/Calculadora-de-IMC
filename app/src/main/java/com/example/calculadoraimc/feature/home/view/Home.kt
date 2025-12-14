package com.example.calculadoraimc.feature.home.view

import MainCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.feature.home.components.IMCCalculatorContainer
import com.example.calculadoraimc.feature.home.components.MetricCard
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var resultIMC by remember { mutableStateOf<IMCData?>(null) }
    var heightUser by remember { mutableIntStateOf(0) }
    var weightUser by remember { mutableDoubleStateOf(0.0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Calculadora de IMC",
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
        ) {
            // Entrada de dados
            IMCCalculatorContainer(
                onResult = {result ->
                resultIMC = result
            })

            Spacer(Modifier.height(28.dp))

            resultIMC?.let {
                HomeContent(it)
            }

        }
    }
}

@Composable
fun HomeContent(result: IMCData) {

    // Card Principal
    MainCard(result)

    Spacer(Modifier.height(28.dp))

//    // Cards de altura e peso
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        MetricCard(
//            Modifier.weight(1f),
//            metrics = MetricCardData.Height(3f),
//        )
//        MetricCard(
//            Modifier.weight(1f),
//            metrics = MetricCardData.Weight(5f),
//        )
//    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme {
        Home()
    }
}