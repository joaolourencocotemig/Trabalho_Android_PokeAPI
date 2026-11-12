package com.example.projetofinalpokeapi.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favoritos",
    indices = [
        androidx.room.Index(
            value = ["pokemonId", "usuarioId"],
            unique = true
        )
    ]
)
data class FavoritoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val pokemonId: Int,

    val nome: String,

    val imagem: String,

    val usuarioId: Int
)