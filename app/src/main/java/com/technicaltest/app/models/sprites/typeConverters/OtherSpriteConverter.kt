package com.technicaltest.app.models.sprites.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technicaltest.app.models.sprites.OtherSprite

public class OtherSpriteConverter {

    /**
     * Convert an [OtherSprite] to a Json
     */
    @TypeConverter
    fun fromOtherSpriteJson(stat: OtherSprite): String {
        return Gson().toJson(stat)
    }

    /**
     * Convert a json to an [OtherSprite]
     */
    @TypeConverter
    fun toOtherSpriteJson(otherSprite: String): OtherSprite {
        val notesType = object : TypeToken<OtherSprite>() {}.type
        return Gson().fromJson<OtherSprite>(otherSprite, notesType)
    }
}
