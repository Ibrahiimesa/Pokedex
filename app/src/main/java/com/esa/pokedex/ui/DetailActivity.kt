package com.esa.pokedex.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.esa.pokedex.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewmodel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokeId = intent.getStringExtra("id")
        val pokeName = intent.getStringExtra("name")
        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$pokeId.png"

        binding.apply {
            tvDetailName.text = pokeName
            Glide.with(this@DetailActivity)
                .load(imageUrl)
                .into(imgAvatar)
        }

        setupView()

        if (pokeId != null) {
            viewmodel.getPokemonDetail(pokeId)
        }
    }

    private fun setupView() {
        viewmodel.pokemonDetail.observe(this) { detailsResponse ->
            detailsResponse?.let { pokemonDetail ->
                binding.apply {
                    val abilities = pokemonDetail.abilities.map { it.ability.name }
                    tvAbilitiesList.text = abilities.joinToString(", ")
                }
            }
        }
    }

}