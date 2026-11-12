package com.example.projetofinalpokeapi.userinterface.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.projetofinalpokeapi.data.model.PokemonResultado

@Composable
fun PokemonCard(
    pokemon: PokemonResultado,
    cor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = pokemon.imagem,
            contentDescription = pokemon.name,
            modifier = Modifier.size(85.dp)
        )

        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}