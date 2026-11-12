package com.example.projetofinalpokeapi.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofinalpokeapi.data.repository.PokemonRepository
import com.example.projetofinalpokeapi.data.model.PokemonResultado
import com.example.projetofinalpokeapi.data.model.PokemonDetalhes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemons =
        MutableStateFlow<List<PokemonResultado>>(emptyList())

    val pokemons = _pokemons.asStateFlow()

    private val _detalhes =
        MutableStateFlow<PokemonDetalhes?>(null)

    val detalhes = _detalhes.asStateFlow()

    private val _carregando = MutableStateFlow(false)
    val carregando = _carregando.asStateFlow()

    private val _erro = MutableStateFlow<String?>(null)
    val erro = _erro.asStateFlow()

    fun carregarPokemons() {
        viewModelScope.launch {
            _carregando.value = true
            _erro.value = null

            try {
                val resposta = repository.listarPokemons()
                _pokemons.value = resposta.results
            } catch (e: Exception) {
                _erro.value = "Erro ao carregar Pokémon. Verifique sua internet."
            } finally {
                _carregando.value = false
            }
        }
    }

    fun buscarPokemon(id: String) {
        viewModelScope.launch {
            _carregando.value = true
            _erro.value = null

            try {
                _detalhes.value = repository.buscarPokemon(id)
            } catch (e: Exception) {
                _erro.value = "Não foi possível carregar os detalhes."
            } finally {
                _carregando.value = false
            }
        }
    }
}