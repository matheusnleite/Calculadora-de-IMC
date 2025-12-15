package com.example.calculadoraimc.feature.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoraimc.data.local.entity.IMCHistory
import com.example.calculadoraimc.data.repository.IMCHistoryRepository
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.model.IMCData
import kotlinx.coroutines.launch
import java.util.Date

data class ValidationState(
    val heightError: Boolean = false,
    val weightError: Boolean = false,
    val ageError: Boolean = false,
    val genderError: Boolean = false,
    val activityLevelError: Boolean = false
)

/** Gemini - início
 * Prompt: "Crie um ViewModel para a tela principal que siga a arquitetura MVVM. Ele deve gerenciar o estado dos campos de entrada (altura, peso, idade, etc.), o estado de validação e o resultado final dos cálculos. Ele também deve interagir com um repositório para salvar os dados."
 */
/**
 * ViewModel para a tela principal (Home).
 *
 * Esta classe é o coração da arquitetura MVVM para a tela de cálculo. Ela é responsável por:
 * - Manter o estado de todos os campos de entrada (altura, peso, idade, etc.) usando o `mutableStateOf` do Compose.
 * - Expor os estados para a UI (View) de forma segura, permitindo apenas a leitura.
 * - Receber eventos da UI através de funções públicas (ex: `onHeightChange`).
 * - Orquestrar a lógica de validação e cálculo.
 * - Comunicar-se com o `IMCHistoryRepository` para persistir os dados.
 *
 * @property repository O repositório para acesso à camada de dados (banco de dados Room).
 */
class HomeViewModel(private val repository: IMCHistoryRepository) : ViewModel() {
    var height by mutableStateOf("")
        private set

    var weight by mutableStateOf("")
        private set

    var age by mutableStateOf("")
        private set

    var gender by mutableStateOf<Gender?>(null)
        private set

    var activityLevel by mutableStateOf<ActivityLevel?>(null)
        private set

    var resultIMC by mutableStateOf<IMCData?>(null)
        private set

    var validationState by mutableStateOf(ValidationState())
        private set

    fun onHeightChange(newHeight: String) {
        if (newHeight.length <= 3) {
            height = newHeight
            validationState = validationState.copy(heightError = false)
        }
    }

    fun onWeightChange(newWeight: String) {
        if (newWeight.length <= 7) {
            weight = newWeight
            validationState = validationState.copy(weightError = false)
        }
    }

    fun onAgeChange(newAge: String) {
        if (newAge.length <= 3) {
            age = newAge
            validationState = validationState.copy(ageError = false)
        }
    }

    fun onGenderSelected(selectedGender: Gender) {
        gender = selectedGender
        validationState = validationState.copy(genderError = false)
    }

    fun onActivityLevelSelected(level: ActivityLevel) {
        activityLevel = level
        validationState = validationState.copy(activityLevelError = false)
    }

    /** Gemini - início
     * Prompt: "Atualize a função `calculate` para primeiro validar todos os campos. Se algum for inválido, atualize um `validationState` e pare. Se tudo for válido, chame as funções de cálculo para IMC, TMB, Peso Ideal e Necessidade Calórica, combine os resultados em um único objeto, atualize o estado da UI e chame a função para salvar no banco de dados."
     */
    /**
     * Executa o processo de validação e cálculo.
     *
     * Esta função é o ponto de entrada principal acionado pelo usuário. Ela primeiro valida
     * todos os campos de entrada (altura, peso, idade, etc.). Se qualquer campo for inválido,
     * ela atualiza o [validationState] para que a UI possa exibir os erros e interrompe a execução.
     *
     * Se todos os dados forem válidos, ela procede com a chamada para as funções de cálculo
     * em [Calculations], combina todos os resultados em um único objeto [IMCData], atualiza o estado
     * [resultIMC] para a UI ser recomposta e, finalmente, chama a função [saveCalculation] para
     * persistir o registro no banco de dados.
     */
    fun calculate() {
        val heightValue = height.toIntOrNull()
        val weightValue = weight.toDoubleOrNull()
        val ageValue = age.toIntOrNull()

        val isHeightInvalid = heightValue == null || heightValue <= 0
        val isWeightInvalid = weightValue == null || weightValue <= 0
        val isAgeInvalid = ageValue == null || ageValue <= 0
        val isGenderInvalid = gender == null
        val isActivityLevelInvalid = activityLevel == null

        validationState = ValidationState(
            heightError = isHeightInvalid,
            weightError = isWeightInvalid,
            ageError = isAgeInvalid,
            genderError = isGenderInvalid,
            activityLevelError = isActivityLevelInvalid
        )

        val hasError = isHeightInvalid || isWeightInvalid || isAgeInvalid || isGenderInvalid || isActivityLevelInvalid
        if (hasError) {
            return
        }

        // Como todos os campos foram validados, podemos usar os valores não nulos com segurança.
        val validatedHeight = heightValue!!
        val validatedWeight = weightValue!!
        val validatedAge = ageValue!!
        val validatedGender = gender!!
        val validatedActivityLevel = activityLevel!!

        Calculations.calculateIMC(height = height, weight = weight) { imcResult ->

            val bmr = Calculations.calculateBMR(validatedWeight, validatedHeight, validatedAge, validatedGender)
            val idealWeight = Calculations.calculateIdealWeight(validatedHeight, validatedGender)
            val dailyCaloricNeed = Calculations.calculateDailyCaloricNeed(bmr, validatedActivityLevel)

            val finalResult = imcResult.copy(
                bmr = bmr,
                idealWeight = idealWeight,
                dailyCaloricNeed = dailyCaloricNeed
            )

            resultIMC = finalResult
            saveCalculation(finalResult)
        }
    }
    /** Gemini - final */

    private fun saveCalculation(result: IMCData) = viewModelScope.launch {
        val history = IMCHistory(
            date = Date(),
            weight = weight.toDoubleOrNull() ?: 0.0,
            height = height.toIntOrNull() ?: 0,
            imc = result.imcValue,
            imcClassification = result.classification,
            age = age.toIntOrNull() ?: 0,
            gender = gender,
            bmr = result.bmr,
            idealWeight = result.idealWeight,
            dailyCaloricNeed = result.dailyCaloricNeed
        )
        repository.insertHistory(history)
    }
}
/** Gemini - final */
