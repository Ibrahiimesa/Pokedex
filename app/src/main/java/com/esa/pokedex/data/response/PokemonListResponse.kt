package com.esa.pokedex.data.response

import com.esa.pokedex.data.model.PokemonModel

data class PokemonListResponse(
    val results: List<PokemonModel>
)
