package com.technicaltest.app.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.technicaltest.app.R
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.adapters.PokemonAdapter
import com.technicaltest.app.ui.utils.VerticalSpaceItemDecoration
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
 */
class MainFragment : Fragment(), XRecyclerView.LoadingListener, PokemonAdapter.OnPokemonSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : PokemonAdapter

    private val pokemonList = mutableListOf<Pokemon>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = PokemonAdapter(requireContext(), pokemonList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.init(requireContext())
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModel.getPokemon()?.observe(this.viewLifecycleOwner) { dataInfo ->
            Timber.d("Observer the dataInfo object. It contains ${dataInfo.pokemonList?.size ?: 0} pokemon")
            pokemonList.addAll(dataInfo.pokemonList ?: emptyList())
            adapter.notifyDataSetChanged()

            // Tells to the recyclerView that the items are loaded,
            // to continue to use the loadMore functionality.
            recycler?.loadMoreComplete()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler?.let {
            it.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
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
        // Do nothing
    }

    override fun onLoadMore() {
        getPokemonList()
    }
}