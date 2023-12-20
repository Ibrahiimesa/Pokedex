package com.esa.pokedex.di

import com.esa.pokedex.repository.Repository
import com.esa.pokedex.data.network.ApiService
import com.esa.pokedex.data.room.AppDatabase
import com.esa.pokedex.data.room.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providePokemonDao(database: AppDatabase): PokemonDao {
        return database.PokemonDao()
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, pokemonDao: PokemonDao): Repository =
        Repository(apiService, pokemonDao)

}