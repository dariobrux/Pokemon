package com.technicaltest.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.technicaltest.app.R
import com.technicaltest.app.models.Pokemon


class PokemonAdapter(private val items: List<Pokemon>) :

    RecyclerView.Adapter<PokemonAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.img.setImageResource(android.R.drawable.ic_delete)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)
    }
}