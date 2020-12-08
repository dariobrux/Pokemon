package com.dariobrux.pokemon.app.data.remote.model.info

data class Types(

    var slot: Int?,

    var type: Type? = Type()
)

data class Type(

    var name: String? = "",

    var url: String? = ""
)
