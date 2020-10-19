package com.technicaltest.app.extensions

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 */
fun String.getIdFromUrl() : Int {
    val splitUrl = this.split("/").toMutableList()
    splitUrl.removeLast()
    return splitUrl.last().toIntOrNull() ?: -1
}