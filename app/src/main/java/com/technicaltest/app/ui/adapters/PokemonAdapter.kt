package com.technicaltest.app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technicaltest.app.R
import com.technicaltest.app.extensions.getIdFromUrl
import com.technicaltest.app.models.Pokemon
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * This is the adapter applied to the RecyclerView in the MainFragment.
 *
 */
class PokemonAdapter(private val context: Context, private val items: List<Pokemon>, private val listener: OnPokemonSelectedListener?) : RecyclerView.Adapter<PokemonAdapter.PostViewHolder>() {

    interface OnPokemonSelectedListener {
        fun onPokemonSelected(pokemon : Pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        val holder =  PostViewHolder(view)
        view.setOnClickListener {
            listener?.onPokemonSelected(items[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val pokemon = items[position]

        val id = pokemon.url?.getIdFromUrl() ?: -1
        val url = String.format(context.getString(R.string.url_pokemon_image), id)
        Glide.with(context).load(url).into(holder.img)

        holder.txtName.text = pokemon.name.capitalize(Locale.getDefault())
        holder.txtNumber.text = id.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtNumber: TextView = itemView.findViewById(R.id.txtNumber)
    }
}