package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.viewmodel.ValidationState
import com.example.calculadoraimc.ui.theme.BlueColor
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IMCCalculatorContainer(
    height: String,
    onHeightChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    gender: Gender?,
    onGenderSelected: (Gender) -> Unit,
    activityLevel: ActivityLevel?,
    onActivityLevelSelected: (ActivityLevel) -> Unit,
    validationState: ValidationState,
    onCalculate: () -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column {
        // Inputs
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OutlinedTextField(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp), value = height, onValueChange = onHeightChange, label = { Text(text = "Altura em cm") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = validationState.heightError)
            OutlinedTextField(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp), value = weight, onValueChange = onWeightChange, label = { Text(text = "Peso em kg") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), isError = validationState.weightError)
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), value = age, onValueChange = onAgeChange, label = { Text(text = "Idade") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = validationState.ageError)

        Spacer(Modifier.height(16.dp))

        // Seletor de sexo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (validationState.genderError) Color.Red else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(modifier = Modifier.weight(1f), onClick = { onGenderSelected(Gender.MALE) }, colors = ButtonDefaults.buttonColors(containerColor = if (gender == Gender.MALE) BlueColor else Color.LightGray)) { Text(text = "Masculino") }
            Button(modifier = Modifier.weight(1f), onClick = { onGenderSelected(Gender.FEMALE) }, colors = ButtonDefaults.buttonColors(containerColor = if (gender == Gender.FEMALE) BlueColor else Color.LightGray)) { Text(text = "Feminino") }
        }

        Spacer(Modifier.height(16.dp))

        // Seletor de Nível de Atividade
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), // Importante para o dropdown funcionar
                readOnly = true,
                value = activityLevel?.displayName ?: "Selecione o nível de atividade",
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                shape = RoundedCornerShape(16.dp),
                isError = validationState.activityLevelError
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                ActivityLevel.values().forEach { level ->
                    DropdownMenuItem(
                        text = { Text(level.displayName) },
                        onClick = {
                            onActivityLevelSelected(level)
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Botao
        Button(modifier = Modifier.fillMaxWidth(), onClick = onCalculate, colors = ButtonDefaults.buttonColors(containerColor = BlueColor, contentColor = Color.White)) {
            Text(text = "Calcular", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IMCCalculatorContainerPreview() {
    CalculadoraIMCTheme {
        var height by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf<Gender?>(null) }
        var activityLevel by remember { mutableStateOf<ActivityLevel?>(null) }

        IMCCalculatorContainer(
            height = height, onHeightChange = { height = it },
            weight = weight, onWeightChange = { weight = it },
            age = age, onAgeChange = { age = it },
            gender = gender, onGenderSelected = { gender = it },
            activityLevel = activityLevel, onActivityLevelSelected = { activityLevel = it },
            validationState = ValidationState(ageError = true), // Exemplo de erro
            onCalculate = {}
        )
    }
}
