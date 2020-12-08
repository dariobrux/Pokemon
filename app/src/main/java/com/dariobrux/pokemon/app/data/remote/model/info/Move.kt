package com.dariobrux.pokemon.app.data.remote.model.info

import com.squareup.moshi.Json

data class Moves(

    var move: Move? = Move(),

    @field:Json(name = "version_group_details")
    var versionGroupDetails: List<VersionGroupDetail>? = emptyList()
)

data class Move(

    var name: String? = "",

    var url: String? = ""
)

data class VersionGroupDetail(

    @field:Json(name = "level_learned_at")
    var levelLearnedAt: Int? = 0,

    @field:Json(name = "move_learn_method")
    var moveLearnMethod: MoveLearnMethod? = MoveLearnMethod(),

    @field:Json(name = "version_group")
    var versionGroup: VersionGroup? = VersionGroup()
)

data class MoveLearnMethod(

    var name: String? = "",

    var url: String? = ""
)

data class VersionGroup(

    var name: String? = "",

    var url: String? = ""
)
