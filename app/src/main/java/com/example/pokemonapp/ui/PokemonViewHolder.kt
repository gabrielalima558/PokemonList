package com.example.pokemonapp.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.model.ResultPokemon

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val text1: TextView = view.findViewById(R.id.text1)
    private val text2: TextView = view.findViewById(R.id.text2)

    private var resultPokemon: Pokemon? = null

    fun bind(resultPokemon: Pokemon?) {
        if (resultPokemon != null) {
            showPokemonData(resultPokemon)
        }
    }

    private fun showPokemonData(resultPokemon: Pokemon) {
        this.resultPokemon = resultPokemon
        text1.text = resultPokemon.name
        text2.text = resultPokemon.url
    }

    companion object {
        //factory method
        //cria o ViewHolder de maneira fácil
        fun create(parent: ViewGroup): PokemonViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_view_item, parent, false)
            return PokemonViewHolder(view)
        }
    }
}