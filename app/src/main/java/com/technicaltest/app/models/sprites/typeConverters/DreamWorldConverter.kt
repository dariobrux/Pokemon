package com.technicaltest.app.models.sprites.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technicaltest.app.models.sprites.DreamWorld
import com.technicaltest.app.models.sprites.OtherSprite

public class DreamWorldConverter {

    /**
     * Convert a [DreamWorld] to a Json
     */
    @TypeConverter
    fun fromJson(obj: DreamWorld): String {
        return Gson().toJson(obj)
    }

    /**
     * Convert a json to an [OtherSprite]
     */
    @TypeConverter
    fun toOtherSpriteJson(string: String): OtherSprite {
        val notesType = object : TypeToken<OtherSprite>() {}.type
        return Gson().fromJson<OtherSprite>(string, notesType)
    }
}
