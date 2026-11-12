package com.example.projetofinalpokeapi.data.repository


import com.example.projetofinalpokeapi.data.local.UsuarioDao
import com.example.projetofinalpokeapi.data.local.UsuarioEntity
import com.example.projetofinalpokeapi.data.local.FavoritoDao
import com.example.projetofinalpokeapi.data.local.FavoritoEntity
import java.security.MessageDigest

class UsuarioRepository(
    private val usuarioDao: UsuarioDao,
    private val favoritoDao: FavoritoDao
) {

    private fun String.sha256(): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(toByteArray())
            .joinToString("") { "%02x".format(it) }
    }

    suspend fun cadastrar(
        nome: String,
        email: String,
        senha: String
    ): String {

        if (nome.isBlank() ||
            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email).matches() ||
            senha.length < 6
        ) {
            return "Preencha os campos corretamente."
        }

        if (usuarioDao.buscarPorEmail(email) != null) {
            return "Este e-mail já está cadastrado."
        }

        usuarioDao.cadastrar(
            UsuarioEntity(
                nome = nome,
                email = email,
                senhaHash = senha.sha256()
            )
        )

        return "Cadastro realizado com sucesso!"
    }

    suspend fun login(
        email: String,
        senha: String
    ): UsuarioEntity? {

        val usuario = usuarioDao.buscarPorEmail(email)

        return if (usuario != null &&
            usuario.senhaHash == senha.sha256()
        ) {
            usuario
        } else {
            null
        }
    }

    suspend fun buscarUsuario(id: Int) =
        usuarioDao.buscarPorId(id)

    suspend fun adicionarFavorito(
        pokemon: FavoritoEntity
    ) {
        favoritoDao.adicionar(pokemon)
    }

    suspend fun listarFavoritos(usuarioId: Int) =
        favoritoDao.listar(usuarioId)

    suspend fun removerFavorito(
        pokemonId: Int,
        usuarioId: Int
    ) {
        favoritoDao.remover(pokemonId, usuarioId)
    }

    suspend fun verificarFavorito(
        pokemonId: Int,
        usuarioId: Int
    ) = favoritoDao.verificar(pokemonId, usuarioId)
}