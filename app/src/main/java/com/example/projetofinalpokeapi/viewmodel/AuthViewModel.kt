package com.example.projetofinalpokeapi.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofinalpokeapi.data.repository.UsuarioRepository
import com.example.projetofinalpokeapi.data.local.UsuarioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _usuario =
        MutableStateFlow<UsuarioEntity?>(null)

    val usuario = _usuario.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem = _mensagem.asStateFlow()

    fun cadastrar(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            _mensagem.value =
                repository.cadastrar(nome, email, senha)
        }
    }

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            val resultado = repository.login(email, senha)

            if (resultado != null) {
                _usuario.value = resultado
                _mensagem.value = null
            } else {
                _mensagem.value = "E-mail ou senha incorretos."
            }
        }
    }

    fun restaurarSessao(id: Int) {
        viewModelScope.launch {
            _usuario.value = repository.buscarUsuario(id)
        }
    }

    fun sair() {
        _usuario.value = null
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}