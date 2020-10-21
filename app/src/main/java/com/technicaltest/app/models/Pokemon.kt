package com.technicaltest.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * This class represents the model of a pokémon.
 * It's used by Room database as table, storing the basic information
 * about the single pokémon.
 *
 */
@Entity(tableName = "pokemon")
class Pokemon : Serializable {

    @PrimaryKey
    var name: String? = ""

    var url: String? = ""
}