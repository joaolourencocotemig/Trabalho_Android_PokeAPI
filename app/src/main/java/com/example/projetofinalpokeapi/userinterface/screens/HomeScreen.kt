package com.example.projetofinalpokeapi.userinterface.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projetofinalpokeapi.data.model.PokemonResultado
import com.example.projetofinalpokeapi.userinterface.components.PokemonCard
import com.example.projetofinalpokeapi.ui.theme.*

@Composable
fun HomeScreen(
    pokemons: List<PokemonResultado>,
    carregando: Boolean,
    erro: String?,
    onBuscar: () -> Unit,
    onPokemonClick: (String) -> Unit
) {
    var pesquisa by remember { mutableStateOf("") }

    val cores = listOf(Verde, Laranja, Azul, Amarelo)

    LaunchedEffect(Unit) {
        onBuscar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .padding(16.dp)
    ) {

        Text(
            "POKÉDEX",
            color = Branco,
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = pesquisa,
            onValueChange = { pesquisa = it },
            label = { Text("Buscar Pokémon...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        when {
            carregando && pokemons.isEmpty() -> {
                CircularProgressIndicator()
            }

            erro != null && pokemons.isEmpty() -> {
                Text(erro, color = Color.Red)

                Button(onClick = onBuscar) {
                    Text("Tentar novamente")
                }
            }

            else -> {
                val filtrados = pokemons.filter {
                    it.name.contains(pesquisa, ignoreCase = true)
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filtrados) { pokemon ->

                        PokemonCard(
                            pokemon = pokemon,
                            cor = cores[pokemon.id % cores.size],
                            onClick = {
                                onPokemonClick(pokemon.id.toString())
                            }
                        )
                    }
                }
            }
        }
    }
}