package com.dariobrux.pokemon.app.data.local.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * Created by Dario Bruzzese on 26/11/2020.
 *
 * This class is the type database model.
 *
 */
@Entity(tableName = "type")
data class TypeEntity(

    @PrimaryKey
    @NonNull
    var name: String = ""

) : Serializable