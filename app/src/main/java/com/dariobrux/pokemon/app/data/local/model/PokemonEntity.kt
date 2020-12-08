package com.dariobrux.pokemon.app.data.local.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * Created by Dario Bruzzese on 26/11/2020.
 *
 * This class is the Pokemon database model.
 *
 */
@Entity(tableName = "pokemon")
data class PokemonEntity(

    @PrimaryKey
    @NonNull
    var name: String = "",

    @NonNull
    var images: List<ImageEntity> = listOf(),

    @NonNull
    var types: List<TypeEntity> = listOf(),

    @NonNull
    var stats: List<StatsEntity> = listOf(),

    @NonNull
    var id: Int = 0,

    @NonNull
    var baseExperience: Int = 0

) : Serializable
