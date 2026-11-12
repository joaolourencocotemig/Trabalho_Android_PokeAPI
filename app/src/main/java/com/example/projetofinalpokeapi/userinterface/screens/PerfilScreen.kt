package com.example.projetofinalpokeapi.userinterface.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projetofinalpokeapi.data.local.UsuarioEntity

@Composable
fun PerfilScreen(
    usuario: UsuarioEntity?,
    onFavoritos: () -> Unit,
    onSair: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(40.dp))

        Text("Meu perfil", color = Color.White)

        Spacer(Modifier.height(40.dp))

        Text("♙", color = Color.White, style = MaterialTheme.typography.displayLarge)

        Spacer(Modifier.height(15.dp))

        Text(
            usuario?.nome ?: "Treinador",
            color = Color.White
        )

        Text(
            usuario?.email ?: "",
            color = Color.Gray
        )

        Spacer(Modifier.height(35.dp))

        Button(
            onClick = onFavoritos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("♡  Meus favoritos")
        }

        OutlinedButton(
            onClick = onSair,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair da conta", color = Color.Red)
        }
    }
}