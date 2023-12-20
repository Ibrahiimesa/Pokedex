package com.esa.pokedex.data.network

import com.esa.pokedex.data.response.PokemonDetailResponse
import com.esa.pokedex.data.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListResponse
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: String): PokemonDetailResponse
}