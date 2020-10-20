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


class PokemonAdapter(private val context : Context, private val items: List<Pokemon>) :

    RecyclerView.Adapter<PokemonAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val pokemon = items[position]
        val url = String.format(context.getString(R.string.url_pokemon_image), pokemon.url?.getIdFromUrl())
        Glide.with(context).load(url).into(holder.img)

        holder.txtName.text = pokemon.name?.capitalize(Locale.getDefault())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
    }
}