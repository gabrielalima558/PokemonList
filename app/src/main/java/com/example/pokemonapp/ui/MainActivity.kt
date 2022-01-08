package com.example.pokemonapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.Injection
import com.example.pokemonapp.R
import com.example.pokemonapp.ui.loadstate.ReposLoadStateAdapter
import com.example.pokemonapp.viewmodel.PokemonViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonViewModel
    private var pokeJob: Job? = null
    private val adapter = PokemonsAdapter()
    private var list: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.list)

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
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter { adapter.retry() }
        )
        list!!.layoutManager = LinearLayoutManager(this)

    }

}