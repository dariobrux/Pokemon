package com.dariobrux.pokemon.app.data.remote.model.info

import com.squareup.moshi.Json

data class Abilities(

    var ability: Ability?,

    @field:Json(name = "is_hidden")
    var isHidden: Boolean?,

    var slot: Int?
)

data class Ability(

    var name: String?,

    var url: String?
)
