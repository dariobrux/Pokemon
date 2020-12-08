package com.dariobrux.pokemon.app.ui.info

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */

@AndroidEntryPoint
class InfoFragment : Fragment() {

//    private var binding: FragmentInfoBinding? = null
//
//    /**
//     * The pokemon object
//     */
//    private lateinit var pokemon: Pokemon
//
//    /**
//     * The ViewModel
//     */
//    private val viewModel: InfoViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        pokemon = requireArguments().getSerializable("pokemon") as Pokemon
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentInfoBinding.inflate(inflater, container, false)
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        binding?.txtName?.text = pokemon.name.capitalize(Locale.getDefault())
//
//        // Get the info of the pokemob.
//        getPokemonData()
//
//
//    }
//
//    /**
//     * Observe the ViewModel LiveData property to get the info of the
//     * pokemon and refresh the layout.
//     */
//    private fun getPokemonData() {
//        viewModel.getPokemonData(pokemon.name, pokemon.url ?: "").observe(this.viewLifecycleOwner) {
//            val pokemonData = it.data ?: return@observe
//            binding?.txtExperience?.text = getString(R.string.base_experience, pokemonData.baseExperience)
//            binding?.txtHeight?.text = getString(R.string.height, pokemonData.height)
//            binding?.txtWeight?.text = getString(R.string.weight, pokemonData.weight)
//
//            Glide.with(requireContext()).asBitmap().load(pokemon.urlPicture).listener(object : RequestListener<Bitmap> {
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
//                    Timber.e("Image loading failed")
//                    return true
//                }
//
//                // When the bitmap is loaded, I get the dominant color of the image
//                // and use it as background color.
//                override fun onResourceReady(bitmap: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    bitmap ?: return true
//                    bitmap.getDominantColor(ContextCompat.getColor(requireContext(), R.color.white)) { color ->
//                        binding?.card?.setCardBackgroundColor(color)
//                        val startColor = requireActivity().toMainActivity()?.mainContainerRoot?.background?.toColorDrawable()?.color ?: return@getDominantColor
//                        binding?.containerRoot?.animateBackgroundColor(startColor, color.changeAlpha(190))
//                    }
//                    return false
//                }
//
//            }).into(thumb)
//        }
//    }
}