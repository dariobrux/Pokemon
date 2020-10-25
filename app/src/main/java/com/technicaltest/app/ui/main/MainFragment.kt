package com.technicaltest.app.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var adapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    private var sort = MutableLiveData(Sort.ID)

    enum class Sort {
        NAME, ID;

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        if (!(activity as MainActivity).isThemeChanged) {
//            getPokemonList()
//        } else {
//            refreshPokemonList()
//        }
//        (activity as MainActivity).isThemeChanged = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(VerticalSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_space)))
            it.adapter = adapter
            it.setLoadingListener(this)
        }

        btnSort?.setOnClickListener {
            sort.value = sort.value!!.inverse()
        }

        if (viewModel.pokemonList.isEmpty()) {
            getPokemonList()
        }

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

    private fun sortById() {
        viewModel.pokemonList.sortBy { pokemon ->
            pokemon.url?.getIdFromUrl()
        }
    }

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

            adapter.notifyDataSetChanged()

            // Tells the recyclerView that the items are loaded,
            // to continue to use the loadMore functionality.
            recycler?.loadMoreComplete()
        }
    }

    private fun sortByName() {
        viewModel.pokemonList.sortBy { pokemon ->
            pokemon.name
        }
    }

    fun refreshPokemonList() {
        viewModel.refreshPokemon()?.observe(this.viewLifecycleOwner) {
            Timber.d("Refresh the pokemon list. Displayed ${it.data?.pokemonList ?: 0} pokemon.")

            viewModel.pokemonList.clear()
            viewModel.pokemonList.addAll(it.data?.pokemonList ?: emptyList())

            // Sort by name
            if (sort.value == Sort.NAME) {
                sortByName()
            } else {
                sortById()
            }

            adapter.notifyDataSetChanged()

            // Tells the recyclerView that the items are refreshed.
            recycler?.refreshComplete()
        }
    }

    override fun onPokemonSelected(pokemon: Pokemon) {
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_infoFragment, Bundle().apply {
            putSerializable("pokemon", pokemon)
        })
    }

    override fun onRefresh() {
        refreshPokemonList()
    }

    override fun onLoadMore() {
        getPokemonList()
    }
}