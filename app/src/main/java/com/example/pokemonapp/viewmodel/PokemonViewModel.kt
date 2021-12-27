package com.example.pokemonapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.model.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    private var currentPokemon: Flow<PagingData<Pokemon>>? = null

    fun getPokemon(): Flow<PagingData<Pokemon>>? {
        val result: Flow<PagingData<Pokemon>>? = pokemonRepository.getPokeResult().cachedIn(viewModelScope)
        currentPokemon = result
        return result
    }
}