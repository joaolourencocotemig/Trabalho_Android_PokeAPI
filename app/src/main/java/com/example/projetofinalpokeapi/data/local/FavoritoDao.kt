package com.example.projetofinalpokeapi.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun adicionar(favorito: FavoritoEntity)

    @Query("SELECT * FROM favoritos WHERE usuarioId = :usuarioId")
    suspend fun listar(usuarioId: Int): List<FavoritoEntity>

    @Query("""
        DELETE FROM favoritos
        WHERE pokemonId = :pokemonId
        AND usuarioId = :usuarioId
    """)
    suspend fun remover(pokemonId: Int, usuarioId: Int)

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM favoritos
            WHERE pokemonId = :pokemonId
            AND usuarioId = :usuarioId
        )
    """)
    suspend fun verificar(pokemonId: Int, usuarioId: Int): Boolean
}