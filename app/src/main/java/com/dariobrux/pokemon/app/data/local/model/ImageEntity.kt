package com.dariobrux.pokemon.app.data.local.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * Created by Dario Bruzzese on 29/11/2020.
 *
 * This class is the image database model.
 *
 */
@Entity(tableName = "image")
data class ImageEntity(

    @PrimaryKey
    @NonNull
    var url: String = ""

) : Serializable