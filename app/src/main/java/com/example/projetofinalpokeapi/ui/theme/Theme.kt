package com.example.projetofinalpokeapi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CoresEscuras = darkColorScheme(
    primary = Vermelho,
    background = Fundo,
    surface = Card,
    onBackground = Branco,
    onSurface = Branco
)

@Composable
fun PokedexTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CoresEscuras,
        content = content
    )
}