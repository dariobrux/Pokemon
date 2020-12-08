package com.dariobrux.pokemon.app.data.remote.model.info

import com.squareup.moshi.Json

data class GameIndices(

    @field:Json(name = "game_index")
    var gameIndex: String? = "",

    var version: Version? = Version()
)

data class Version(

    var name: String? = "",

    var url: String? = ""
)
