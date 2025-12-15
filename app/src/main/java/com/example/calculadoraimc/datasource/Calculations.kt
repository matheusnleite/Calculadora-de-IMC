package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.model.IMCData
import kotlin.math.roundToInt

object Calculations {

    /** Gemini - início
     * Prompt: "Crie a lógica de cálculo do IMC em uma função. A função deve receber altura (em cm) e peso (em kg) como Strings, fazer a validação, calcular o IMC, determinar a classificação (Abaixo do peso, Peso normal, etc.) e retornar um objeto IMCData com o resultado."
     */
    @SuppressLint("DefaultLocale")
    fun calculateIMC(height: String, weight: String, response: (IMCData) -> Unit) {
        val heightValue = height.toIntOrNull()
        val weightValue = weight.toDoubleOrNull()

        if (heightValue == null || weightValue == null || heightValue <= 0 || weightValue <= 0) {
            response(IMCData(imc = "---", classification = "Valores inválidos", imcValue = 0.0))
            return
        }

        val heightInMeters = heightValue / 100.0
        val imc = weightValue / (heightInMeters * heightInMeters)
        val imcFormatted = String.format("%.1f", imc)

        val imcClassification = when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 25 -> "Peso normal"
            imc < 30 -> "Sobrepeso"
            imc < 35 -> "Obesidade Grau I"
            imc < 40 -> "Obesidade Grau II"
            else -> "Obesidade Grau III"
        }

        response(IMCData(imcFormatted, imcClassification, imc))
    }
    /** Gemini - final */

    /** Gemini - início
     * Prompt: "Crie uma função para calcular a Taxa Metabólica Basal (TMB) usando a fórmula de Mifflin-St Jeor. A função deve receber peso (Double), altura (Int), idade (Int) e sexo (enum Gender) e retornar a TMB como um Int. Documente a fórmula no código."
     */
    fun calculateBMR(weight: Double, height: Int, age: Int, gender: Gender): Int {
        // Fórmula de Mifflin-St Jeor
        val bmr = (10 * weight) + (6.25 * height) - (5 * age)
        return if (gender == Gender.MALE) {
            (bmr + 5).roundToInt()
        } else {
            (bmr - 161).roundToInt()
        }
    }
    /** Gemini - final */

    /** Gemini - início
     * Prompt: "Preciso de uma função que calcule o Peso Ideal usando a Fórmula de Devine. Ela deve receber altura (Int) e sexo (Gender), e retornar uma String formatada com a faixa de peso ideal (ex: '65.0 - 79.5 kg')."
     */
    @SuppressLint("DefaultLocale")
    fun calculateIdealWeight(height: Int, gender: Gender): String {
        val heightInInches = height / 2.54
        val fiveFeetInInches = 60

        if (heightInInches <= fiveFeetInInches) {
            return if (gender == Gender.MALE) "~50 kg" else "~45.5 kg"
        }

        val inchesOverFiveFeet = heightInInches - fiveFeetInInches
        val baseWeight = if (gender == Gender.MALE) 50.0 else 45.5
        val idealWeight = baseWeight + (2.3 * inchesOverFiveFeet)

        val lowerBound = idealWeight * 0.9
        val upperBound = idealWeight * 1.1

        return "${String.format("%.1f", lowerBound)} - ${String.format("%.1f", upperBound)} kg"
    }
    /** Gemini - final */

    fun calculateDailyCaloricNeed(bmr: Int, activityLevel: ActivityLevel): Int {
        return (bmr * activityLevel.factor).roundToInt()
    }
}
