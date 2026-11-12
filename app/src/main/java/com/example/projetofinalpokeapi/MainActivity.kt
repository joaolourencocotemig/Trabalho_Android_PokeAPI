package com.example.projetofinalpokeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember

import com.example.projetofinalpokeapi.data.local.AppDatabase
import com.example.projetofinalpokeapi.data.repository.PokemonRepository
import com.example.projetofinalpokeapi.data.repository.UsuarioRepository

import com.example.projetofinalpokeapi.network.RetrofitInstance

import com.example.projetofinalpokeapi.viewmodel.PokemonViewModel
import com.example.projetofinalpokeapi.viewmodel.AuthViewModel
import com.example.projetofinalpokeapi.viewmodel.FavoritosViewModel

import com.example.projetofinalpokeapi.userinterface.navigation.AppNavigation
import com.example.projetofinalpokeapi.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencias = getSharedPreferences(
            "pokedex_sessao",
            MODE_PRIVATE
        )

        setContent {

            val banco = remember {
                AppDatabase.getDatabase(applicationContext)
            }

            val pokemonRepository = remember {
                PokemonRepository(RetrofitInstance.api)
            }

            val usuarioRepository = remember {
                UsuarioRepository(
                    banco.usuarioDao(),
                    banco.favoritoDao()
                )
            }

            val pokemonViewModel = remember {
                PokemonViewModel(pokemonRepository)
            }

            val authViewModel = remember {
                AuthViewModel(usuarioRepository)
            }

            val favoritosViewModel = remember {
                FavoritosViewModel(usuarioRepository)
            }

            val usuarioSalvo = remember {
                preferencias.getInt("usuario_id", -1)
                    .takeIf { it != -1 }
            }

            PokedexTheme {

                AppNavigation(
                    pokemonViewModel = pokemonViewModel,
                    authViewModel = authViewModel,
                    favoritosViewModel = favoritosViewModel,

                    salvarSessao = { id ->
                        preferencias.edit()
                            .putInt("usuario_id", id)
                            .apply()
                    },

                    limparSessao = {
                        preferencias.edit()
                            .remove("usuario_id")
                            .apply()
                    },

                    usuarioSalvo = usuarioSalvo
                )
            }
        }
    }
}