package com.example.projetofinalpokeapi.userinterface.navigation


import kotlinx.serialization.Serializable

@Serializable
data object Login

@Serializable
data object Cadastro

@Serializable
data object Home

@Serializable
data object Favoritos

@Serializable
data object Perfil

@Serializable
data class Detalhes(
    val pokemonId: String
)