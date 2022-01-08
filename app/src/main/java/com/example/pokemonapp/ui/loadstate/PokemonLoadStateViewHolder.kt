package com.example.pokemonapp.ui.loadstate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R

class PokemonLoadStateViewHolder(
    view: View, retry: () -> Unit
) : RecyclerView.ViewHolder(view) {
    private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    private val retryButton: Button = view.findViewById(R.id.retry_button)
    private val errorMsg: TextView = view.findViewById(R.id.error_msg)

    init {
        retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PokemonLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_load_state_footer_view_item, parent, false)
            return PokemonLoadStateViewHolder(view, retry)
        }
    }
}