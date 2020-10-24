package com.technicaltest.app.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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

    @SerializedName("base_experience")
    var baseExperience = 0

    var name: String? = ""
}