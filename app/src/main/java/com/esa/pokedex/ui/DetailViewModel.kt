package com.esa.pokedex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esa.pokedex.data.response.PokemonDetailResponse
import com.esa.pokedex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokemonDetailResponse>()
    val pokemonDetail: LiveData<PokemonDetailResponse> get() = _pokemonDetail

    fun getPokemonDetail(id: String) {
        viewModelScope.launch {
            try {
                val detailResponse = repository.getPokemonDetail(id)
                withContext(Dispatchers.Main) {
                    _pokemonDetail.value = detailResponse
                }
            } catch (e: Exception) {
                Log.e("DetailViewModel", "$e")
            }
        }
    }
}