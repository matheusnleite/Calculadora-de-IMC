package com.example.calculadoraimc.feature.detail.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.data.local.entity.IMCHistory
import com.example.calculadoraimc.feature.detail.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val details by viewModel.details.collectAsState()
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(details?.let { formatter.format(it.date) } ?: "Carregando...") },
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            details?.let {
                DetailContent(history = it)
            } ?: CircularProgressIndicator()
        }
    }
}

@Composable
fun DetailContent(history: IMCHistory) {
    // Card do IMC
    DetailCard(title = "IMC (${history.imcClassification})") {
        Text(String.format("%.1f", history.imc), fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Card da TMB
    history.bmr?.let {
        DetailCard(title = "Taxa Metabólica Basal") {
            Text("$it kcal/dia", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    // Card do Peso Ideal
    history.idealWeight?.let {
        DetailCard(title = "Peso Ideal (Devine)") {
            Text(it, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    // Card da Necessidade Calórica
    history.dailyCaloricNeed?.let {
        DetailCard(title = "Necessidade Calórica Diária") {
            Text("$it kcal/dia", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    // Card de Informações Gerais
    DetailCard(title = "Dados da Medição") {
        InfoRow("Peso:", "${history.weight} kg")
        InfoRow("Altura:", "${history.height} cm")
        InfoRow("Idade:", "${history.age} anos")
        InfoRow("Sexo:", history.gender?.name?.replaceFirstChar { char -> char.uppercase() } ?: "N/A")
    }
}

@Composable
private fun DetailCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(value)
    }
}
