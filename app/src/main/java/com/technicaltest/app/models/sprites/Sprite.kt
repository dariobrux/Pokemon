package com.technicaltest.app.models.sprites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.technicaltest.app.models.sprites.typeConverters.DreamWorldConverter
import com.technicaltest.app.models.sprites.typeConverters.OfficialArtworkConverter
import com.technicaltest.app.models.sprites.typeConverters.OtherSpriteConverter

/**
 *
 * Created by Dario Bruzzese on 24/10/2020.
 *
 */

@Entity(tableName = "sprite")
class Sprite {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("back_default")
    var backDefault: String? = ""

    @SerializedName("back_female")
    var backFemale: String? = ""

    @SerializedName("back_shiny")
    var backShiny: String? = ""

    @SerializedName("back_shiny_female")
    var backShinyFemale: String? = ""

    @SerializedName("front_default")
    var frontDefault: String? = ""

    @SerializedName("front_female")
    var frontFemale: String? = ""

    @SerializedName("front_shiny")
    var frontShiny: String? = ""

    @SerializedName("front_shiny_female")
    var frontShinyFemale: String? = ""

    @SerializedName("other")
    @TypeConverters(OtherSpriteConverter::class)
    var otherSprites: OtherSprite? = null

}

@Entity(tableName = "otherSprite")
class OtherSprite {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("dream_world")
    @TypeConverters(DreamWorldConverter::class)
    @Embedded
    var dreamWorld: DreamWorld? = null

    @SerializedName("official-artwork")
    @TypeConverters(OfficialArtworkConverter::class)
    @Embedded
    var officialArtwork: OfficialArtwork? = null

}

@Entity(tableName = "dreamWorld")
class DreamWorld {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("front_default")
    var frontDefault: String? = ""

    @SerializedName("front_female")
    var frontFemale: String? = ""

}

@Entity(tableName = "officialArtwork")
class OfficialArtwork {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("front_default")
    var frontDefault: String? = ""

}