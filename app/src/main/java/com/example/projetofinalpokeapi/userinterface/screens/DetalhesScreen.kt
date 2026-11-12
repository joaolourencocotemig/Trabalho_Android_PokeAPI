package com.example.projetofinalpokeapi.userinterface.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.projetofinalpokeapi.data.model.PokemonDetalhes

@Composable
fun DetalhesScreen(
    pokemon: PokemonDetalhes?,
    carregando: Boolean,
    erro: String?,
    onVoltar: () -> Unit,
    onFavoritar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(Modifier.fillMaxWidth()) {
            TextButton(onClick = onVoltar) {
                Text("← Voltar", color = Color.White)
            }
        }

        if (carregando) {
            CircularProgressIndicator()
        } else if (pokemon != null) {

            AsyncImage(
                model = pokemon.sprites.front_default,
                contentDescription = pokemon.name,
                modifier = Modifier.size(190.dp)
            )

            Text(
                pokemon.name.replaceFirstChar { it.uppercase() },
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                "Nº ${pokemon.id.toString().padStart(3, '0')}",
                color = Color.Gray
            )

            pokemon.types.forEach {
                Text(
                    it.type.name.replaceFirstChar { c -> c.uppercase() },
                    color = Color.Yellow
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Altura: ${pokemon.height / 10.0} m",
                color = Color.White
            )

            Text(
                "Peso: ${pokemon.weight / 10.0} kg",
                color = Color.White
            )

            Spacer(Modifier.height(20.dp))

            Button(onClick = onFavoritar) {
                Text("Adicionar aos favoritos")
            }

        } else {
            Text(erro ?: "Não foi possível carregar o Pokémon.", color = Color.Red)
        }
    }
}