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
import java.util.*

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

        txtName?.text = pokemon.name.capitalize(Locale.getDefault())

        viewModel.getPokemonData(pokemon.name, pokemon.url ?: "").observe(this.viewLifecycleOwner) {
            val pokemonData = it.data ?: return@observe
            txtExperience?.text = getString(R.string.base_experience, pokemonData.baseExperience)
            txtHeight?.text = getString(R.string.height, pokemonData.height)
            txtWeight?.text = getString(R.string.weight, pokemonData.weight)

            pokemonData.sprites?.otherSprites?.officialArtwork?.frontDefault?.let {url ->
                Glide.with(requireContext()).load(url).into(img)
            }
        }
    }

}