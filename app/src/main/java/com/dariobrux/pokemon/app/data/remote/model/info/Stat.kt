package com.dariobrux.pokemon.app.data.remote.model.info

import com.squareup.moshi.Json

data class Stats(

    @field:Json(name = "base_stat")
    var baseStat : Int?,

    var effort : Int?,

    var stat : Stat? = Stat()
)

data class Stat(

    var name: String? = "",

    var url: String? = ""
)
