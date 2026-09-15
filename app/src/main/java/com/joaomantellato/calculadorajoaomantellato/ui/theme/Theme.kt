package com.joaomantellato.calculadorajoaomantellato.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MinimalColorScheme = lightColorScheme(
    background = MinimalBackground,
    onBackground = MinimalDisplayText,
    surface = MinimalSurface,
    surfaceVariant = MinimalDisplayBackground,
    primary = MinimalOperatorButton,
    onPrimary = MinimalOperatorText,
    secondary = MinimalNumericButton,
    onSecondary = MinimalNumericText,
    tertiary = MinimalFunctionButton,
    onTertiary = MinimalFunctionText,
    error = MinimalClearButton,
    onError = MinimalClearText
)

@Composable
fun CalculadoraJoãoMantellatoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MinimalColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}