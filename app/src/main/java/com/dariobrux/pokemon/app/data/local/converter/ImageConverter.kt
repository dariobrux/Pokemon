package com.dariobrux.pokemon.app.data.local.converter

import androidx.room.TypeConverter
import com.dariobrux.pokemon.app.data.local.model.ImageEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 *
 * Created by Dario Bruzzese on 29/11/2020.
 *
 * This class is the de/serializer of the ImageEntity. It's used by Database to convert
 * object <-> string.
 *
 * It uses Moshi.
 *
 */
class ImageConverter {

    private val moshi = Moshi.Builder().build()
    private val type: Type = Types.newParameterizedType(MutableList::class.java, ImageEntity::class.java)

    /**
     * Convert the list to json.
     * @return the representation of the list in json.
     */
    @TypeConverter
    fun toJson(list: List<ImageEntity>): String {
        val adapter = moshi.adapter<List<ImageEntity>>(type)
        return adapter.toJson(list)
    }

    /**
     * Convert the json to list of objects.
     * @return the object's list
     */
    @TypeConverter
    fun fromJson(str: String): List<ImageEntity> {
        val adapter = moshi.adapter<List<ImageEntity>>(type)
        return adapter.fromJson(str) ?: listOf()
    }
}