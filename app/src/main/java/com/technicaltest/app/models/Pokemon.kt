package com.technicaltest.app.models

import androidx.annotation.NonNull
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
    @NonNull
    var name: String = ""

    var url: String? = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Pokemon
        if (name != other.name) return false
        if (url != other.url) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }
}