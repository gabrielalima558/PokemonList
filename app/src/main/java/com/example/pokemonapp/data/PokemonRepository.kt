package com.example.pokemonapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemonapp.api.PokemonService
import com.example.pokemonapp.model.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val service: PokemonService) {

    fun getPokeResult(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(service) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}