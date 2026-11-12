package com.example.projetofinalpokeapi.data.model


data class PokemonDetalhes(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<PokemonTipo>
)

data class Sprites(
    val front_default: String?
)

data class PokemonTipo(
    val type: Tipo
)

data class Tipo(
    val name: String
)