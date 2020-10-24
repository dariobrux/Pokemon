package com.technicaltest.app.ui.info

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.technicaltest.app.R
import com.technicaltest.app.extensions.getIdFromUrl
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.info_fragment.*

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private lateinit var pokemon: Pokemon

    private val viewModel: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = requireArguments().getSerializable("pokemon") as Pokemon
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = pokemon.url?.getIdFromUrl() ?: -1
        val url = String.format(requireContext().getString(R.string.url_pokemon_image), id)
        Glide.with(requireContext()).load(url).into(img)

        txtName?.text = pokemon.name

        viewModel.getPokemonData(pokemon.name, pokemon.url ?: "").observe(this.viewLifecycleOwner) {
            val pokemonData = it.data ?: return@observe
            txtExperience?.text = pokemonData.baseExperience.toString()
        }
    }

}