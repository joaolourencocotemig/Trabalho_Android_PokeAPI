package com.example.projetofinalpokeapi.network


import com.example.projetofinalpokeapi.data.model.Pokemon
import com.example.projetofinalpokeapi.data.model.PokemonDetalhes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun listarPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Pokemon

    @GET("pokemon/{id}")
    suspend fun buscarPokemon(
        @Path("id") id: String
    ): PokemonDetalhes
}