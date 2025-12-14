import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.components.IMCGraphic
import com.example.calculadoraimc.feature.home.components.IconTag
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.ui.theme.BlackFont
import com.example.calculadoraimc.ui.theme.BlueColor
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@Composable
fun MainCard(result: IMCData) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BlueColor
        ),
        shape = RoundedCornerShape(
            size = 36.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp, 24.dp, 20.dp, 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // PRIMEIRA COLUNA
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    // Tag superior
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconTag(
                            icon = rememberVectorPainter(Icons.Rounded.FavoriteBorder),
                            contentDescription = "Icone superior"
                        )
                        Text(
                            text = "Seu IMC",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp
                            ),
                            color = BlackFont,
                            softWrap = false
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = result.imc,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 56.sp
                        ),
                        color = BlackFont
                    )

                    Text(
                        text = result.classification,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 24.sp
                        )
                    )
                }

                // SEGUNDA COLUNA
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.height(32.dp))

                    IMCGraphic(result.imcValue)
                }
            }

            // Seção da TMB
            result.bmr?.let { bmr ->
                HealthMetricItem(
                    icon = rememberVectorPainter(Icons.Default.LocalFireDepartment),
                    label = "Taxa Metabólica Basal (TMB)",
                    value = "$bmr kcal / dia"
                )
            }

            // Seção do Peso Ideal
            result.idealWeight?.let { idealWeight ->
                HealthMetricItem(
                    icon = rememberVectorPainter(Icons.Default.Balance),
                    label = "Peso Ideal (Fórmula de Devine)",
                    value = idealWeight
                )
            }

            // Seção da Necessidade Calórica
            result.dailyCaloricNeed?.let { caloricNeed ->
                HealthMetricItem(
                    icon = rememberVectorPainter(Icons.Default.LocalDining),
                    label = "Necessidade Calórica Diária",
                    value = "$caloricNeed kcal / dia"
                )
            }
        }
    }
}

@Composable
private fun HealthMetricItem(icon: androidx.compose.ui.graphics.painter.Painter, label: String, value: String) {
    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = Color.White.copy(alpha = 0.5f))
    Spacer(modifier = Modifier.height(24.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconTag(icon = icon, contentDescription = "$label icon")
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            color = BlackFont
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp
        ),
        color = BlackFont
    )
}


@Preview(showBackground = true)
@Composable
private fun MainCardPreview() {
    CalculadoraIMCTheme {
        MainCard(
            IMCData(
                imc = "24.5",
                classification = "Peso normal",
                imcValue = 24.5,
                bmr = 1650,
                idealWeight = "65.0 - 79.5 kg",
                dailyCaloricNeed = 2269
            )
        )
    }
}
