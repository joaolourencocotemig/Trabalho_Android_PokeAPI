package com.example.projetofinalpokeapi.userinterface.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.projetofinalpokeapi.data.local.FavoritoEntity

@Composable
fun FavoritosScreen(
    favoritos: List<FavoritoEntity>,
    onCarregar: () -> Unit,
    onRemover: (Int) -> Unit,
    onDetalhes: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        onCarregar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Text(
            "Meus favoritos",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )

        LazyColumn {
            items(favoritos) { pokemon ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDetalhes(pokemon.pokemonId.toString())
                        }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row {
                        AsyncImage(
                            model = pokemon.imagem,
                            contentDescription = pokemon.nome,
                            modifier = Modifier.size(60.dp)
                        )

                        Column {
                            Text(pokemon.nome, color = Color.White)

                            Text(
                                "Ver detalhes",
                                color = Color.Gray
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            onRemover(pokemon.pokemonId)
                        }
                    ) {
                        Text("♡", color = Color.Red)
                    }
                }
            }
        }
    }
}