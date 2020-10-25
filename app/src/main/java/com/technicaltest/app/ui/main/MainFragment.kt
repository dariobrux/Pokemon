package com.technicaltest.app.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.technicaltest.app.MainActivity
import com.technicaltest.app.R
import com.technicaltest.app.extensions.getIdFromUrl
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.utils.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber
import javax.inject.Inject


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

    /**
     * The Adapter to show the items in list.
     */
    private lateinit var adapter: MainAdapter

    /**
     * The ViewModel
     */
    private val viewModel: MainViewModel by viewModels()

    /**
     * The current sort mode. The first time the list is sorted for ID.
     */
    private var sort = MutableLiveData(Sort.ID)

    enum class Sort {
        NAME, ID;

        /**
         * Invert sort.
         * ID -> Name
         * Name -> ID
         */
        fun inverse(): Sort {
            return if (this == ID) NAME
            else ID
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MainAdapter(requireContext(), viewModel.pokemonList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Set the RecyclerView with its LayoutManager, ItemDecorator, Adapter and callbacks.
        recycler?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(VerticalSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_space)))
            it.adapter = adapter
            it.setLoadingListener(this)
        }

        // Set the button Sort with the background color and the callback.
        btnSort?.let {
            it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
            it.setOnClickListener {
                sort.value = sort.value!!.inverse()
            }
        }

        // At this point, I must observe the ViewModel to get the updated list
        // of pokemon only if the current list is empty. I let do in this way
        // because, when I change the theme, the application is refreshed, and
        // I could incur in any bug.
        if (viewModel.pokemonList.isEmpty()) {
            getPokemonList()
        }

        // Observe the sort mode to refresh the list and the sort button.
        sort.observe(this.viewLifecycleOwner) {
            btnSort?.text = if (it == Sort.NAME) {
                sortByName()
                getString(R.string.num_sorting)
            } else {
                sortById()
                getString(R.string.a_z_sorting)
            }
            adapter.notifyDataSetChanged()
        }
    }

    /**
     * Sort the list by the pokemon id.
     */
    private fun sortById() {
        viewModel.pokemonList.sortBy { pokemon ->
            pokemon.url?.getIdFromUrl()
        }
    }

    /**
     * Sort the list by the pokemon name.
     */
    private fun sortByName() {
        viewModel.pokemonList.sortBy { pokemon ->
            pokemon.name
        }
    }

    /**
     * Observe the ViewModel to get the list of the pokemon to show.
     */
    private fun getPokemonList() {
        viewModel.getPokemon()?.observe(this.viewLifecycleOwner) {
            Timber.d("Observer the dataInfo object. It contains ${it.data?.pokemonList?.size ?: 0} pokemon")
            viewModel.pokemonList.addAll(it.data?.pokemonList ?: emptyList())

            // Sort by name
            if (sort.value == Sort.NAME) {
                sortByName()
            } else {
                sortById()
            }

            // Refresh the adapter.
            adapter.notifyDataSetChanged()

            // Tells the recyclerView that the items are loaded,
            // to continue to use the loadMore functionality.
            recycler?.loadMoreComplete()
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

            // Sort by name
            if (sort.value == Sort.NAME) {
                sortByName()
            } else {
                sortById()
            }

            // Refresh the adapter.
            adapter.notifyDataSetChanged()

            // Tells the recyclerView that the items are refreshed.
            recycler?.refreshComplete()
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