package com.example.projetofinalpokeapi.data.model


data class Pokemon(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResultado>
)

data class PokemonResultado(
    val name: String,
    val url: String
) {
    val id: Int
        get() = url.trimEnd('/')
            .substringAfterLast('/')
            .toIntOrNull() ?: 0

    val imagem: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}