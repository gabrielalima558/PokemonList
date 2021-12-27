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
//    private val stars: TextView = view.findViewById(R.id.repo_stars)
//    private val language: TextView = view.findViewById(R.id.repo_language)
//    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var resultPokemon: Pokemon? = null

    init {
        //abre a página recebida pela url que vem no next, no caso não será necessário
        view.setOnClickListener {
            resultPokemon?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(resultPokemon: Pokemon?) {
//        if (resultPokemon == null) {
//            val resources = itemView.resources
//            name.text = resources.getString(R.string.loading)
//            description.visibility = View.GONE
//            language.visibility = View.GONE
//            stars.text = resources.getString(R.string.unknown)
//            forks.text = resources.getString(R.string.unknown)
//        } else {
        if (resultPokemon != null) {
            showPokemonData(resultPokemon)
        }
//        }
    }

    private fun showPokemonData(resultPokemon: Pokemon) {
        this.resultPokemon = resultPokemon
        text1.text = resultPokemon.name
        text2.text = resultPokemon.url
//
//        // if the description is missing, hide the TextView
//        var descriptionVisibility = View.GONE
//        if (resultPokemon.description != null) {
//            description.text = resultPokemon.description
//            descriptionVisibility = View.VISIBLE
//        }
//        description.visibility = descriptionVisibility
//
//        stars.text = resultPokemon.stars.toString()
//        forks.text = resultPokemon.forks.toString()
//
//        // if the language is missing, hide the label and the value
//        var languageVisibility = View.GONE
//        if (!resultPokemon.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, resultPokemon.language)
//            languageVisibility = View.VISIBLE
//        }
//        language.visibility = languageVisibility
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