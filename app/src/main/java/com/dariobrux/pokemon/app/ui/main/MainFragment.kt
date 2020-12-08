package com.dariobrux.pokemon.app.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.common.Resource
import com.dariobrux.pokemon.app.data.local.model.PokemonEntity
import com.dariobrux.pokemon.app.databinding.FragmentMainBinding
import com.dariobrux.pokemon.app.ui.util.GridSpaceItemDecoration
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 * This is the main fragment, where the layout shows
 * the list of all pokemon. It limits the items to display
 * each time. So, after having scrolled the list, an HTTP request must be
 * done to retrieve another group of pokemon.
 *
 * I declare this class with AndroidEntryPoint annotation, to tell the activity that
 * we will be injecting dependency here. Without this, the DI won't work.
 */

@AndroidEntryPoint
class MainFragment : Fragment(), XRecyclerView.LoadingListener, MainAdapter.OnPokemonSelectedListener {

    private var binding: FragmentMainBinding? = null

    /**
     * The Adapter to show the items in list.
     */
    private var adapter: MainAdapter? = null

    /**
     * The ViewModel
     */
    private val viewModel: MainViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MainAdapter(requireContext(), mutableListOf(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.mainRecycler?.let {
            it.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            it.addItemDecoration(GridSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_padding)))
            it.setLoadingMoreProgressStyle(ProgressStyle.Pacman)
            it.adapter = adapter
            it.setLoadingListener(this)
        }

        getPokemonList()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetOffset()
    }

    /**
     * Observe the ViewModel to get the list of the pokemon to show.
     */
    private fun getPokemonList() {
        viewModel.getPokemonList().observe(this.viewLifecycleOwner) {

            if (it.status == Resource.Status.SUCCESS) {

                val pokemonList = it.data ?: listOf()
                Timber.d("Observer the dataInfo object. It contains ${pokemonList.size} pokemon")

                adapter?.let { adapter ->
                    adapter.items.addAll(pokemonList)
                    adapter.notifyDataSetChanged()
                }
            }

            // Tells the recyclerView that the items are loaded,
            // to continue to use the loadMore functionality.
            binding?.mainRecycler?.loadMoreComplete()
        }
    }

    /**
     * Refresh the screen, reloading the pokemon list from the first value
     */
    private fun refreshPokemonList() {
//        viewModel.refreshPokemon()?.observe(this.viewLifecycleOwner) {
//            Timber.d("Refresh the pokemon list. Displayed ${it.data?.pokemonList ?: 0} pokemon.")
//
//            // Clear the list and reinsert everything retrieved from the ViewModel.
//            viewModel.pokemonList.clear()
//            viewModel.pokemonList.addAll(it.data?.pokemonList ?: emptyList())
//
//            // Refresh the adapter.
//            viewModel.adapter?.notifyDataSetChanged()
//
//            // Tells the recyclerView that the items are refreshed.
//            binding?.mainRecycler?.refreshComplete()
//        }
    }

    /**
     * Invoked when a pokemon in the list is tapped.
     * It opens the screen with all the info.
     * @param pokemon the pokemon tapped.
     */
    override fun onPokemonSelected(pokemon: PokemonEntity) {
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_infoFragment, Bundle().apply {
            putSerializable("pokemon", pokemon)
        })
    }

    /**
     * Invoked when I pull to refresh.
     * It refresh the list.
     */
    override fun onRefresh() {
        refreshPokemonList()
    }

    /**
     * Invoked when the list reach the limit scrollable value.
     * It invoke the viewModel to retrieve other pokemon.
     */
    override fun onLoadMore() {
        getPokemonList()
    }
}