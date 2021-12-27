package com.example.pokemonapp.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.pokemonapp.model.Pokemon

class PokemonsAdapter : PagingDataAdapter<Pokemon, PokemonViewHolder>(POKEMON_COMPARATOR) {
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonItem = getItem(position)
        if (pokemonItem != null) {
            holder.bind(pokemonItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.create(parent)
    }

    companion object {
        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon
            ): Boolean = oldItem == newItem


        }
    }
}


