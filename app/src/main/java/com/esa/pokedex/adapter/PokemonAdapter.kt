package com.esa.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esa.pokedex.data.room.PokemonEntity
import com.esa.pokedex.databinding.PokemonItemBinding

class PokemonAdapter(private var pokemon: List<PokemonEntity>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    var onItemClick: ((PokemonEntity) -> Unit)? = null
    fun updateData(newData: List<PokemonEntity>) {
        pokemon = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemon[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            pokemon.let { onItemClick?.invoke(it) }
        }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    class ViewHolder(val binding: PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonEntity) {
            binding.apply {
                tvName.text = pokemon.name
            }
        }
    }
}