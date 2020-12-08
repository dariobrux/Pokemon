package com.dariobrux.pokemon.app.data.remote.model.info

import com.dariobrux.pokemon.app.data.remote.model.info.*
import com.squareup.moshi.Json

data class PokemonInfo(

    var id: Int?,

    var name: String? = "",

    var abilities: List<Abilities>? = emptyList(),

    @field:Json(name = "base_experience")
    var baseExperience: Int? = 0,

    var forms: List<Form>? = emptyList(),

    @field:Json(name = "game_indices")
    var gameIndices: List<GameIndices>? = emptyList(),

    var height: Int? = 0,

    @field:Json(name = "is_default")
    var isDefault: Boolean? = true,

    @field:Json(name = "location_area_encounters")
    var location: String? = "",

    var moves: List<Moves>? = emptyList(),

    var order: Int? = 0,

    var species: Specie? = Specie(),

    var sprites: Sprite? = Sprite(),

    var types: List<Types>? = emptyList(),

    var stats: List<Stats>? = emptyList(),

    var weight: Int? = 0,
)
