package com.esa.pokedex.repository

import com.esa.pokedex.data.network.ApiService
import com.esa.pokedex.data.response.PokemonDetailResponse
import com.esa.pokedex.data.room.PokemonDao
import com.esa.pokedex.data.room.PokemonEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao
) {
    suspend fun getPokemonList() {
        val response = apiService.getPokemonList()
        val pokemonListFromApi = response.results

        pokemonListFromApi.forEach {
            val pokemonEntity = PokemonEntity(getIdFromUrl(it.url), it.name, it.url)
            pokemonDao.insertPokemon(pokemonEntity)
        }
    }

    suspend fun getPokemonDetail(id: String): PokemonDetailResponse =
        apiService.getPokemonDetail(id)

    private fun getIdFromUrl(url: String): Int {
        return url.split("/").filter { it.isNotEmpty() }.last().toInt()
    }

    val pokemonList: Flow<List<PokemonEntity>> = pokemonDao.getAllPokemon()

    suspend fun filterPokemonList(query: String): List<PokemonEntity> {
        return if (query.isNotEmpty()) {
            pokemonDao.searchPokemon("%$query%") // Implement a suitable search query in your DAO
        } else {
            emptyList()
        }
    }
}
