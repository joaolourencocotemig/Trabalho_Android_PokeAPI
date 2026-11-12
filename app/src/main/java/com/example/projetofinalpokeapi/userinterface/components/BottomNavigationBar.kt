package com.example.projetofinalpokeapi.userinterface.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    telaAtual: String,
    navegar: (String) -> Unit
) {
    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color.Black
    ) {

        NavigationBarItem(
            selected = telaAtual == "home",
            onClick = { navegar("home") },
            icon = { Icon(Icons.Default.GridView, null) },
            label = { Text("Pokédex") }
        )

        NavigationBarItem(
            selected = telaAtual == "favoritos",
            onClick = { navegar("favoritos") },
            icon = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Favoritos") }
        )

        NavigationBarItem(
            selected = telaAtual == "perfil",
            onClick = { navegar("perfil") },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Perfil") }
        )
    }
}