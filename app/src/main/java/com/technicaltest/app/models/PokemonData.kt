package com.technicaltest.app.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.technicaltest.app.models.sprites.Sprite
import com.technicaltest.app.models.sprites.typeConverters.SpriteConverter

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 */
@Entity(tableName = "pokemonData")
class PokemonData {
    @PrimaryKey
    @NonNull
    var id: Int = 0

    var name: String? = ""

    @SerializedName("base_experience")
    var baseExperience = 0

    var height = 0

    var weight = 0

    @TypeConverters(SpriteConverter::class)
    var sprites: Sprite? = null
}