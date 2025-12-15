package com.example.calculadoraimc.feature.home.view

import MainCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.feature.home.components.IMCCalculatorContainer
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.feature.home.viewmodel.HomeViewModel
import com.example.calculadoraimc.ui.theme.BlueColor
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.ui.theme.WhiteTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: HomeViewModel,
    onNavigateToHistory: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Calculadora de IMC",
                        color = WhiteTag
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Histórico",
                            tint = WhiteTag
                        )
                    }
                    IconButton(onClick = onNavigateToHelp) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Ajuda",
                            tint = WhiteTag
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BlueColor
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Entrada de dados
            IMCCalculatorContainer(
                height = viewModel.height,
                onHeightChange = viewModel::onHeightChange,
                weight = viewModel.weight,
                onWeightChange = viewModel::onWeightChange,
                age = viewModel.age,
                onAgeChange = viewModel::onAgeChange,
                gender = viewModel.gender,
                onGenderSelected = viewModel::onGenderSelected,
                activityLevel = viewModel.activityLevel,
                onActivityLevelSelected = viewModel::onActivityLevelSelected,
                validationState = viewModel.validationState,
                onCalculate = viewModel::calculate
            )

            Spacer(Modifier.height(28.dp))

            viewModel.resultIMC?.let {
                HomeContent(it)
            }
        }
    }
}

@Composable
fun HomeContent(result: IMCData) {
    MainCard(result)

    Spacer(Modifier.height(28.dp))
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme {
        // A preview está quebrada porque Home requer um ViewModel que não pode ser instanciado aqui.
    }
}
