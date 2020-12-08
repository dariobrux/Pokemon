package com.dariobrux.pokemon.app.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.common.extension.loadImage
import com.dariobrux.pokemon.app.data.local.model.PokemonEntity
import com.dariobrux.pokemon.app.databinding.FragmentInfoBinding
import com.dariobrux.pokemon.app.ui.util.StatsSpaceItemDecoration
import com.dariobrux.pokemon.app.ui.util.TypeSpaceItemDecoration
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 24/11/2020.
 *
 * This fragment displays he Pokemon info.
 *
 */
class InfoFragment : DialogFragment() {

    private var binding: FragmentInfoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pokemon = arguments?.getSerializable("pokemon") as? PokemonEntity ?: return

        with(binding!!) {
            card.transitionName = pokemon.name

            img.loadImage(requireContext(), pokemon.images.first().url) {
                infoContainerRoot.setBackgroundColor(it)
                card.setCardBackgroundColor(it)
            }

            txtNumber.text = getString(R.string.format_number, pokemon.id)

            txt.text = pokemon.name.capitalize(Locale.getDefault())

            txtBaseExperience.text = getString(R.string.format_exp, pokemon.baseExperience)

            recyclerTypes.let {
                it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                it.addItemDecoration(TypeSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_padding)))
                it.adapter = TypeAdapter(requireContext(), pokemon.types)
            }

            recyclerStats.let {
                it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                it.addItemDecoration(StatsSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_padding)))
                it.adapter = StatsAdapter(requireContext(), pokemon.stats)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.recyclerStats?.adapter = null
        binding?.recyclerTypes?.adapter = null
        binding = null
    }
}