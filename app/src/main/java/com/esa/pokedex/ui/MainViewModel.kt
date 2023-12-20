package com.esa.pokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.esa.pokedex.data.room.PokemonEntity
import com.esa.pokedex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val pokemonList: LiveData<List<PokemonEntity>> = repository.pokemonList.asLiveData()

    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                repository.getPokemonList()
            } catch (_: Exception) {

            }
        }
    }

    private val _filteredPokemonList = MutableStateFlow<List<PokemonEntity>>(emptyList())
    val filteredPokemonList: StateFlow<List<PokemonEntity>> get() = _filteredPokemonList

    fun filterPokemonList(query: String) {
        viewModelScope.launch {
            val filteredList = repository.filterPokemonList(query)
            _filteredPokemonList.value = filteredList
        }
    }

}