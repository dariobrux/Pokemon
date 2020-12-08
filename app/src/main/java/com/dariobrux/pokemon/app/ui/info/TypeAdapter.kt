package com.dariobrux.pokemon.app.ui.info

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.dariobrux.pokemon.app.common.extension.loadImage
import com.dariobrux.pokemon.app.common.mapper.TypeMapper
import com.dariobrux.pokemon.app.data.local.model.TypeEntity
import com.dariobrux.pokemon.app.databinding.TypeItemListBinding
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 28/11/2020.
 *
 * This adapter displays the Pokemon type items.
 *
 */
class TypeAdapter(private val context: Context, private val items: List<TypeEntity>) : RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return TypeViewHolder(TypeItemListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TypeViewHolder(private val binding: TypeItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeEntity) = with(binding) {
            imgType.loadImage(context, item.name)
            txtType.text = item.name.capitalize(Locale.getDefault())
            ViewCompat.setBackgroundTintList(circle, ColorStateList.valueOf(TypeMapper.getTypeColor(item.name)))
        }
    }
}