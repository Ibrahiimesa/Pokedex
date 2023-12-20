package com.esa.pokedex.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonItem: PokemonEntity)

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE name LIKE :query")
    suspend fun searchPokemon(query: String): List<PokemonEntity>

    @Delete
    suspend fun deleteFavorite(favoriteItem: PokemonEntity)
}