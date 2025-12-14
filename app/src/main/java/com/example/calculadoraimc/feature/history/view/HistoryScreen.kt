package com.example.calculadoraimc.feature.history.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.data.local.entity.IMCHistory
import com.example.calculadoraimc.feature.history.components.IMCHistoryChart
import com.example.calculadoraimc.feature.history.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onBack: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    val historyList by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico e Evolução") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            // Adiciona o gráfico como o primeiro item da lista rolável
            if (historyList.size > 1) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Evolução do IMC",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        IMCHistoryChart(historyList = historyList)
                    }
                    HorizontalDivider()
                }
            }

            // Adiciona os itens individuais do histórico
            items(historyList) {
                HistoryItem(
                    history = it,
                    onClick = { onItemClick(it.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryItem(
    history: IMCHistory,
    onClick: () -> Unit
) {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Data: ${formatter.format(history.date)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "IMC: ${String.format("%.1f", history.imc)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = history.imcClassification,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Peso: ${history.weight} kg")
                    Text("Altura: ${history.height} cm")
                }
            }

            if (history.bmr != null || history.idealWeight != null || history.dailyCaloricNeed != null) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                history.bmr?.let {
                    HealthMetricRow(label = "TMB", value = "$it kcal/dia")
                }

                history.idealWeight?.let {
                    HealthMetricRow(label = "Peso Ideal", value = it)
                }

                history.dailyCaloricNeed?.let {
                    HealthMetricRow(label = "Nec. Calórica", value = "$it kcal/dia")
                }
            }
        }
    }
}

@Composable
private fun HealthMetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
