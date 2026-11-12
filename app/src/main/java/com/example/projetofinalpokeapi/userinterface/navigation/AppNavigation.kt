
package com.example.projetofinalpokeapi.userinterface.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.navigation.toRoute

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.projetofinalpokeapi.viewmodel.PokemonViewModel
import com.example.projetofinalpokeapi.viewmodel.AuthViewModel
import com.example.projetofinalpokeapi.viewmodel.FavoritosViewModel

import com.example.projetofinalpokeapi.data.local.FavoritoEntity
import com.example.projetofinalpokeapi.userinterface.screens.*
import com.example.projetofinalpokeapi.userinterface.components.BottomNavigationBar

@Composable
fun AppNavigation(
    pokemonViewModel: PokemonViewModel,
    authViewModel: AuthViewModel,
    favoritosViewModel: FavoritosViewModel,
    salvarSessao: (Int) -> Unit,
    limparSessao: () -> Unit,
    usuarioSalvo: Int?
) {

    val navController = rememberNavController()

    // Estados do usuário
    val usuario by authViewModel.usuario.collectAsState()
    val mensagem by authViewModel.mensagem.collectAsState()

    // Estados dos Pokémon
    val pokemons by pokemonViewModel.pokemons.collectAsState()
    val detalhes by pokemonViewModel.detalhes.collectAsState()
    val carregando by pokemonViewModel.carregando.collectAsState()
    val erro by pokemonViewModel.erro.collectAsState()

    // Estados dos favoritos
    val favoritos by favoritosViewModel.favoritos.collectAsState()

    // Restaurar sessão salva
    LaunchedEffect(Unit) {
        if (usuarioSalvo != null) {
            authViewModel.restaurarSessao(usuarioSalvo)
        }
    }

    // Quando o usuário entrar, salvar sessão e abrir Home
    LaunchedEffect(usuario?.id) {
        usuario?.let { treinador ->

            salvarSessao(treinador.id)

            navController.navigate(Home) {
                popUpTo<Login> {
                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }

    // Identificar tela atual
    val entrada by navController.currentBackStackEntryAsState()
    val destino = entrada?.destination

    // Verificar se deve mostrar a barra inferior
    val rotaAtual = destino?.route

    val mostrarBarra =
        rotaAtual?.contains("Home") == true ||
                rotaAtual?.contains("Favoritos") == true ||
                rotaAtual?.contains("Perfil") == true

    Scaffold(
        bottomBar = {

            if (mostrarBarra) {

                BottomNavigationBar(
                    telaAtual = when {
                        rotaAtual?.contains("Home") == true -> "home"
                        rotaAtual?.contains("Favoritos") == true -> "favoritos"
                        else -> "perfil"
                    },

                    navegar = { tela ->

                        val rota = when (tela) {
                            "home" -> Home
                            "favoritos" -> Favoritos
                            else -> Perfil
                        }

                        navController.navigate(rota) {

                            popUpTo<Home> {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Login,
            modifier = Modifier.padding(padding)
        ) {

            // LOGIN
            composable<Login> {

                LoginScreen(
                    mensagem = mensagem,

                    onEntrar = { email, senha ->
                        authViewModel.login(email, senha)
                    },

                    onCadastro = {
                        authViewModel.limparMensagem()
                        navController.navigate(Cadastro)
                    }
                )
            }

            // CADASTRO
            composable<Cadastro> {

                CadastroScreen(
                    mensagem = mensagem,

                    onCadastrar = { nome, email, senha ->
                        authViewModel.cadastrar(
                            nome,
                            email,
                            senha
                        )
                    },

                    onVoltar = {
                        authViewModel.limparMensagem()
                        navController.popBackStack()
                    }
                )
            }

            // HOME - LISTAGEM DE POKÉMON
            composable<Home> {

                HomeScreen(
                    pokemons = pokemons,
                    carregando = carregando,
                    erro = erro,

                    onBuscar = {
                        if (pokemons.isEmpty()) {
                            pokemonViewModel.carregarPokemons()
                        }
                    },

                    onPokemonClick = { id ->

                        pokemonViewModel.buscarPokemon(id)

                        navController.navigate(
                            Detalhes(id)
                        )
                    }
                )
            }

            // DETALHES DO POKÉMON
            composable<Detalhes> { entradaDetalhes ->

                val rota = entradaDetalhes.toRoute<Detalhes>()

                LaunchedEffect(rota.pokemonId) {
                    pokemonViewModel.buscarPokemon(
                        rota.pokemonId
                    )
                }

                DetalhesScreen(
                    pokemon = detalhes,
                    carregando = carregando,
                    erro = erro,

                    onVoltar = {
                        navController.popBackStack()
                    },

                    onFavoritar = {

                        usuario?.let { treinador ->

                            detalhes?.let { pokemon ->

                                favoritosViewModel.adicionar(
                                    FavoritoEntity(
                                        pokemonId = pokemon.id,
                                        nome = pokemon.name,
                                        imagem = pokemon.sprites.front_default.orEmpty(),
                                        usuarioId = treinador.id
                                    )
                                )
                            }
                        }
                    }
                )
            }

            // FAVORITOS
            composable<Favoritos> {

                FavoritosScreen(
                    favoritos = favoritos,

                    onCarregar = {
                        usuario?.let { treinador ->
                            favoritosViewModel.carregar(
                                treinador.id
                            )
                        }
                    },

                    onRemover = { id ->
                        usuario?.let { treinador ->
                            favoritosViewModel.remover(
                                id,
                                treinador.id
                            )
                        }
                    },

                    onDetalhes = { id ->

                        pokemonViewModel.buscarPokemon(id)

                        navController.navigate(
                            Detalhes(id)
                        )
                    }
                )
            }

            // PERFIL
            composable<Perfil> {

                PerfilScreen(
                    usuario = usuario,

                    onFavoritos = {
                        navController.navigate(Favoritos)
                    },

                    onSair = {

                        limparSessao()
                        authViewModel.sair()

                        navController.navigate(Login) {

                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
