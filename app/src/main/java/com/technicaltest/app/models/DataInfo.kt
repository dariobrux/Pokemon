package com.technicaltest.app.models

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 */
class DataInfo {
    var count: Int? = 0
    var next: String? = ""
    var previous: String? = ""

    @SerializedName("results")
    var pokemonList: List<Pokemon>? = null
}