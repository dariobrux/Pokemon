package com.dariobrux.pokemon.app.data.models.sprites.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.dariobrux.pokemon.app.data.models.sprites.Sprite

public class SpriteConverter {

    /**
     * Convert a [Sprite] to a Json
     */
    @TypeConverter
    fun fromOtherSpriteJson(stat: Sprite): String {
        return Gson().toJson(stat)
    }

    /**
     * Convert a json to a [Sprite]
     */
    @TypeConverter
    fun toOtherSpriteJson(string: String): Sprite {
        val notesType = object : TypeToken<Sprite>() {}.type
        return Gson().fromJson<Sprite>(string, notesType)
    }
}
