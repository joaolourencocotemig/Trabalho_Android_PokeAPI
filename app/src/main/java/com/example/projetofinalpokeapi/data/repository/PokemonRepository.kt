package com.example.projetofinalpokeapi.data.repository


import com.example.projetofinalpokeapi.network.PokemonApi

class PokemonRepository(
    private val api: PokemonApi
) {

    suspend fun listarPokemons() =
        api.listarPokemons()

    suspend fun buscarPokemon(id: String) =
        api.buscarPokemon(id)
}