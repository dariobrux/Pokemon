package com.technicaltest.app.ui.info

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.technicaltest.app.R
import com.technicaltest.app.extensions.changeAlpha
import com.technicaltest.app.extensions.getDominantColor
import com.technicaltest.app.models.Pokemon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.info_fragment.*
import timber.log.Timber
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */

@AndroidEntryPoint
class InfoFragment : Fragment() {

    /**
     * The pokemon object
     */
    private lateinit var pokemon: Pokemon

    /**
     * The ViewModel
     */
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

        // Get the info of the pokemob.
        getPokemonData()
    }

    /**
     * Observe the ViewModel LiveData property to get the info of the
     * pokemon and refresh the layout.
     */
    private fun getPokemonData() {
        viewModel.getPokemonData(pokemon.name, pokemon.url ?: "").observe(this.viewLifecycleOwner) {
            val pokemonData = it.data ?: return@observe
            txtExperience?.text = getString(R.string.base_experience, pokemonData.baseExperience)
            txtHeight?.text = getString(R.string.height, pokemonData.height)
            txtWeight?.text = getString(R.string.weight, pokemonData.weight)

            Glide.with(requireContext()).asBitmap().load(viewModel.getPictureUrl(pokemonData)).listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    Timber.e("Image loading failed")
                    return true
                }

                // When the bitmap is loaded, I get the dominant color of the image
                // and use it as background color.
                override fun onResourceReady(bitmap: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    bitmap ?: return true
                    bitmap.getDominantColor(ContextCompat.getColor(requireContext(), R.color.white)) { color ->
                        card?.setCardBackgroundColor(color)
                        containerRoot?.setBackgroundColor(color.changeAlpha(190))
                    }
                    return false
                }

            }).into(thumb)
        }
    }
}