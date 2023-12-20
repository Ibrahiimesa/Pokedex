package com.esa.pokedex.data.response

data class PokemonDetailResponse(
    val abilities: List<Ability>
)

data class Ability(
    val ability: AbilityDetails
)

data class AbilityDetails(
    val name: String
)
