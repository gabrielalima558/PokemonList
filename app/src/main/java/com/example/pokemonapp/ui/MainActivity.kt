package com.example.pokemonapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.Injection
import com.example.pokemonapp.R
import com.example.pokemonapp.ui.loadstate.PokemonLoadStateAdapter
import com.example.pokemonapp.viewmodel.PokemonViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonViewModel
    private var pokeJob: Job? = null
    private val adapter = PokemonsAdapter()
    private var list: RecyclerView? = null
    private var retryButton: Button? = null
    private var progressBar: ProgressBar? = null
    private var emptyList: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        emptyList = findViewById(R.id.emptyList)
        list = findViewById(R.id.list)
        retryButton = findViewById(R.id.retry_button)
        retryButton?.setOnClickListener { adapter.retry() }

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(PokemonViewModel::class.java)
        initAdapter()
        getList()


    }

    fun getList() {
        pokeJob?.cancel()
        pokeJob = lifecycleScope.launch {
            viewModel.getPokemon()?.collect {
                adapter.submitData(it)
            }
        }
    }

    fun initAdapter(){
        list!!.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PokemonLoadStateAdapter { adapter.retry() },
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )
        list!!.layoutManager = LinearLayoutManager(this)
        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            list?.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            retryButton?.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun showEmptyList(isListEmpty: Boolean) {
        if (isListEmpty) {
            emptyList?.visibility = View.VISIBLE
        }
    }

}