package com.dariobrux.pokemon.app.ui.main

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
import com.dariobrux.pokemon.app.data.models.Pokemon
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
     * The ViewModel
     */
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.adapter == null) {
            viewModel.adapter = MainAdapter(requireContext(), viewModel.pokemonList, this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Set the RecyclerView with its LayoutManager, ItemDecorator, Adapter and callbacks.
        binding?.mainRecycler?.let {
            it.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            it.addItemDecoration(GridSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_padding)))
            it.setLoadingMoreProgressStyle(ProgressStyle.Pacman)
            it.adapter = viewModel.adapter
            it.setLoadingListener(this)
        }

        // At this point, I must observe the ViewModel to get the updated list
        // of pokemon only if the current list is empty. I let do in this way
        // because, when I change the theme, the application is refreshed, and
        // I could incur in any bug.
        if (viewModel.pokemonList.isEmpty()) {
            getPokemonList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetOffset()
    }

    /**
     * Observe the ViewModel to get the list of the pokemon to show.
     */
    private fun getPokemonList() {
        viewModel.getPokemon()?.observe(this.viewLifecycleOwner) {
            Timber.d("Observer the dataInfo object. It contains ${it.data?.pokemonList?.size ?: 0} pokemon")
            viewModel.pokemonList.addAll(it.data?.pokemonList ?: emptyList())

            // Refresh the adapter.
            viewModel.adapter?.notifyDataSetChanged()

            // Tells the recyclerView that the items are loaded,
            // to continue to use the loadMore functionality.
            binding?.mainRecycler?.loadMoreComplete()
        }
    }

    /**
     * Refresh the screen, reloading the pokemon list from the first value
     */
    private fun refreshPokemonList() {
        viewModel.refreshPokemon()?.observe(this.viewLifecycleOwner) {
            Timber.d("Refresh the pokemon list. Displayed ${it.data?.pokemonList ?: 0} pokemon.")

            // Clear the list and reinsert everything retrieved from the ViewModel.
            viewModel.pokemonList.clear()
            viewModel.pokemonList.addAll(it.data?.pokemonList ?: emptyList())

            // Refresh the adapter.
            viewModel.adapter?.notifyDataSetChanged()

            // Tells the recyclerView that the items are refreshed.
            binding?.mainRecycler?.refreshComplete()
        }
    }

    /**
     * Invoked when a pokemon in the list is tapped.
     * It opens the screen with all the info.
     * @param pokemon the pokemon tapped.
     */
    override fun onPokemonSelected(pokemon: Pokemon) {
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