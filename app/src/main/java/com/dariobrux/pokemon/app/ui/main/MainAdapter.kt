package com.dariobrux.pokemon.app.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dariobrux.pokemon.app.common.extensions.changeAlpha
import com.dariobrux.pokemon.app.common.extensions.loadImage
import com.dariobrux.pokemon.app.data.local.model.PokemonEntity
import com.dariobrux.pokemon.app.databinding.ItemPokemonBinding
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * This is the adapter applied to the RecyclerView in the MainFragment.
 *
 */
class MainAdapter(private val context: Context, var items: MutableList<PokemonEntity>, private val listener: OnPokemonSelectedListener?) : RecyclerView.Adapter<MainAdapter.PokemonViewHolder>() {

    interface OnPokemonSelectedListener {
        fun onPokemonSelected(pokemon: PokemonEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonEntity) = with(binding) {
            card.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
            txt.text = item.name.capitalize(Locale.getDefault())
            img.loadImage(context, item.images.first().url) {
                card.setCardBackgroundColor(it.changeAlpha(220))
            }
            card.setOnClickListener {
                listener?.onPokemonSelected(item)
            }
        }
    }
}