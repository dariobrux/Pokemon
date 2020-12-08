package com.dariobrux.pokemon.app.common.mapper

import android.graphics.Color

/**
 *
 * Created by Dario Bruzzese on 27/11/2020.
 *
 * This object class is a mapper for Type object.
 *
 */
object TypeMapper {

    /**
     * Get the color associated to the Pokemon type.
     * @param name the type name.
     * @return the corresponding unique color for a specific type.
     */
    fun getTypeColor(name: String): Int {
        return Color.parseColor(
            when (name) {
                "bug" -> "#92BC2C"
                "dark" -> "#5A5761"
                "dragon" -> "#0D69C7"
                "electric" -> "#F3D94E"
                "fairy" -> "#EE91E6"
                "fighting" -> "#D3425F"
                "fire" -> "#FCA54C"
                "flying" -> "#A0BBEC"
                "ghost" -> "#5F6EBC"
                "grass" -> "#60BD58"
                "ground" -> "#D97C4C"
                "ice" -> "#75D0C1"
                "normal" -> "#A0A29F"
                "poison" -> "#B763CF"
                "psychic" -> "#FA8581"
                "rock" -> "#C9BB8A"
                "steel" -> "#5694A3"
                "water" -> "#559CDF"
                else -> "#ffffff"
            }
        )
    }
}