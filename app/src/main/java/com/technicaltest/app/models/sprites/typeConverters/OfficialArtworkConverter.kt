package com.technicaltest.app.models.sprites.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technicaltest.app.models.sprites.OfficialArtwork
import com.technicaltest.app.models.sprites.OtherSprite

public class OfficialArtworkConverter {

    /**
     * Convert a [OfficialArtwork] to a Json
     */
    @TypeConverter
    fun fromJson(obj: OfficialArtwork): String {
        return Gson().toJson(obj)
    }

    /**
     * Convert a json to an [OfficialArtwork]
     */
    @TypeConverter
    fun toOtherSpriteJson(string: String): OfficialArtwork {
        val notesType = object : TypeToken<OfficialArtwork>() {}.type
        return Gson().fromJson<OfficialArtwork>(string, notesType)
    }
}
