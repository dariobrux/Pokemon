package com.technicaltest.app.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.technicaltest.app.R
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.utils.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
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

    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: MainAdapter

    private val pokemonList = mutableListOf<Pokemon>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MainAdapter(requireContext(), pokemonList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getPokemonList()
    }

    private fun getPokemonList() {
        viewModel.getPokemon()?.observe(this.viewLifecycleOwner) {
            Timber.d("Observer the dataInfo object. It contains ${it.data?.pokemonList?.size ?: 0} pokemon")
            pokemonList.addAll(it.data?.pokemonList ?: emptyList())
            adapter.notifyDataSetChanged()

            // Tells the recyclerView that the items are loaded,
            // to continue to use the loadMore functionality.
            recycler?.loadMoreComplete()
        }
    }

    private fun refreshPokemonList() {
        viewModel.refreshPokemon()?.observe(this.viewLifecycleOwner) {
            Timber.d("Refresh the pokemon list. Displayed ${it.data?.pokemonList ?: 0} pokemon.")

            pokemonList.clear()
            pokemonList.addAll(it.data?.pokemonList ?: emptyList())
            adapter.notifyDataSetChanged()

            // Tells the recyclerView that the items are refreshed.
            recycler?.refreshComplete()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
//            it.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            it.addItemDecoration(VerticalSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_space)))
            it.adapter = adapter
            it.setLoadingListener(this)
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