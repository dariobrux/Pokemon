package com.dariobrux.pokemon.app.data.remote.model.info

import com.squareup.moshi.Json

data class Sprite(

    @field:Json(name = "back_default")
    var backDefault: String? = "",

    @field:Json(name = "back_female")
    var backFemale: String? = "",

    @field:Json(name = "back_shiny")
    var backShiny: String? = "",

    @field:Json(name = "back_shiny_female")
    var backShinyFemale: String? = "",

    @field:Json(name = "front_default")
    var frontDefault: String? = "",

    @field:Json(name = "front_female")
    var frontFemale: String? = "",

    @field:Json(name = "front_shiny")
    var frontShiny: String? = "",

    @field:Json(name = "front_shiny_female")
    var frontShinyFemale: String? = "",

    var other: Other? = Other(),
)

data class Other(

    @field:Json(name = "dream_world")
    var dreamWorld: DreamWorld? = DreamWorld(),

    @field:Json(name = "official-artwork")
    var officialArtwork: OfficialArtwork? = OfficialArtwork(),
)

data class DreamWorld(

    @field:Json(name = "front_default")
    var frontDefault: String? = "",

    @field:Json(name = "front_female")
    var frontFemale: String? = "",
)

data class OfficialArtwork(

    @field:Json(name = "front_default")
    var frontDefault: String? = "",
)