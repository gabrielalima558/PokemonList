package com.example.pokemonapp.ui.loadstate

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.pokemonapp.ui.loadstate.PokemonLoadStateViewHolder

class PokemonLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PokemonLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PokemonLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PokemonLoadStateViewHolder {
        return PokemonLoadStateViewHolder.create(parent, retry)
    }
}