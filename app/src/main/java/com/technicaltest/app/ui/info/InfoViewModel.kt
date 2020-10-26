package com.technicaltest.app.ui.info

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.technicaltest.app.models.PokemonData
import com.technicaltest.app.ui.main.MainRepository

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class InfoViewModel @ViewModelInject constructor(private val infoRepository: InfoRepository) : ViewModel() {

    /**
     * @param name the name of the pokemon
     * @param url the url to call to get the info.
     * @return a [PokemonData] observable object with the info.
     */
    fun getPokemonData(name: String, url: String) = infoRepository.getPokemonData(name, url)

    /**
     * Get the url from which get the picture.
     * The image is the official artwork.
     */
    fun getPictureUrl(pokemonData : PokemonData) = pokemonData.sprites?.otherSprites?.officialArtwork?.frontDefault ?: ""
}