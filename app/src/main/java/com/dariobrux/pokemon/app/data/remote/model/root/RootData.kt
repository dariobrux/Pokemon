package com.dariobrux.pokemon.app.data.remote.model.root

data class RootData(

    var count: Int?,

    var next: String?,

    var previous: String?,

    var results: List<Results>? = emptyList()
)