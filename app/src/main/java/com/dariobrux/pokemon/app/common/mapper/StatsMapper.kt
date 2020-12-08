package com.dariobrux.pokemon.app.common.mapper

import android.graphics.Color
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 27/11/2020.
 *
 * This object class is a mapper for Stats object.
 *
 */
object StatsMapper {

    /**
     * @param name the stats name
     * @return a pair containing the stats abbreviation and corresponding color.
     */
    fun getStatsAbbreviationColor(name: String): Pair<String, Int> {
        return when (name.toLowerCase(Locale.getDefault())) {
            "attack" -> "ATK" to Color.parseColor("#f06292") // Pink 300
            "defense" -> "DEF" to Color.parseColor("#7986cb") // Indigo 300
            "special-attack" -> "SP. ATK" to Color.parseColor("#4db6ac") // Teal 300
            "special-defense" -> "SP. DEF" to Color.parseColor("#ffd54f") // Amber 300
            "speed" -> "SPD" to Color.parseColor("#a1887f") // Brown 300
            else -> name to Color.parseColor("#4dd0e1") // Cyan 300
        }
    }
}