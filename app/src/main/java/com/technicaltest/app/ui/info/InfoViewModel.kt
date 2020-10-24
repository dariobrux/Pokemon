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
class InfoViewModel @ViewModelInject constructor(private val infoRepository: InfoRepository): ViewModel() {

    fun getPokemonData(name: String, url: String) = infoRepository.getPokemonData(name, url)
}