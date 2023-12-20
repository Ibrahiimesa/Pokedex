package com.esa.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.esa.pokedex.adapter.PokemonAdapter
import com.esa.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var pokemonAdapter: PokemonAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        viewModel.fetchPokemonList()
        setupSearch()
    }

    private fun setupSearch() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        setupView()
                    } else {
                        viewModel.filterPokemonList(newText)
                    }
                }
                return true
            }
        })

        lifecycleScope.launch {
            viewModel.filteredPokemonList.collectLatest { filteredList ->
                pokemonAdapter.updateData(filteredList)
            }
        }
    }

    private fun setupView() {
        pokemonAdapter = PokemonAdapter(emptyList())
        binding.rvPokemon.apply {

            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = pokemonAdapter
        }
        viewModel.pokemonList.observe(this) {
            pokemonAdapter.updateData(it)
        }

        pokemonAdapter.onItemClick = { item ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", item.id.toString())
            intent.putExtra("name", item.name)
            startActivity(intent)
        }
    }
}