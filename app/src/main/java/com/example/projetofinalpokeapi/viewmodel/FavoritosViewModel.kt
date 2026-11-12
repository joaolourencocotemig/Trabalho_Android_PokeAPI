package com.example.projetofinalpokeapi.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofinalpokeapi.data.repository.UsuarioRepository
import com.example.projetofinalpokeapi.data.local.FavoritoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritosViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _favoritos =
        MutableStateFlow<List<FavoritoEntity>>(emptyList())

    val favoritos = _favoritos.asStateFlow()

    fun carregar(usuarioId: Int) {
        viewModelScope.launch {
            _favoritos.value =
                repository.listarFavoritos(usuarioId)
        }
    }

    fun adicionar(favorito: FavoritoEntity) {
        viewModelScope.launch {
            repository.adicionarFavorito(favorito)
            carregar(favorito.usuarioId)
        }
    }

    fun remover(pokemonId: Int, usuarioId: Int) {
        viewModelScope.launch {
            repository.removerFavorito(pokemonId, usuarioId)
            carregar(usuarioId)
        }
    }
}