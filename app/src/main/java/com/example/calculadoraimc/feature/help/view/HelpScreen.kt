package com.example.calculadoraimc.feature.help.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajuda e Informações") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HelpCard(
                title = "Índice de Massa Corporal (IMC)",
                formula = "IMC = Peso (kg) / (Altura (m) * Altura (m))",
                description = "O IMC é uma medida internacional usada para avaliar se uma pessoa está no seu peso ideal. Ele relaciona o peso e a altura."
            )
            Spacer(modifier = Modifier.height(16.dp))
            HelpCard(
                title = "Taxa Metabólica Basal (TMB)",
                formula = "Fórmula de Mifflin-St Jeor",
                description = "A TMB representa o número de calorias que seu corpo queima em repouso durante um dia. As fórmulas são:\nHomens: (10 × peso) + (6.25 × altura) - (5 × idade) + 5\nMulheres: (10 × peso) + (6.25 × altura) - (5 × idade) - 161"
            )
            Spacer(modifier = Modifier.height(16.dp))
            HelpCard(
                title = "Peso Ideal",
                formula = "Fórmula de Devine",
                description = "Uma estimativa de uma faixa de peso saudável com base na altura e no sexo.\nHomens: 50 kg + 2.3 kg por polegada acima de 5 pés.\nMulheres: 45.5 kg + 2.3 kg por polegada acima de 5 pés."
            )
            Spacer(modifier = Modifier.height(16.dp))
            HelpCard(
                title = "Necessidade Calórica Diária",
                formula = "Necessidade = TMB × Fator de Atividade",
                description = "Estima o total de calorias que você deve consumir por dia para manter seu peso atual, com base no seu nível de atividade física."
            )
        }
    }
}

@Composable
private fun HelpCard(title: String, formula: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = formula, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
