package com.dariobrux.pokemon.app.data.local.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * Created by Dario Bruzzese on 26/11/2020.
 *
 * This class is the stats database model.
 *
 */
@Entity(tableName = "stats")
data class StatsEntity(

    @PrimaryKey
    @NonNull
    var name: String = "",

    @NonNull
    var value: Int = 0

) : Serializable