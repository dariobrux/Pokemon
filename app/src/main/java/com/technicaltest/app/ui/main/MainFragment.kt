package com.technicaltest.app.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.technicaltest.app.R
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.adapters.PokemonAdapter
import com.technicaltest.app.ui.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment(), XRecyclerView.LoadingListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : PokemonAdapter

    private val pokemonList = mutableListOf<Pokemon>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = PokemonAdapter(requireContext(), pokemonList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.init()
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModel.getPokemonRepository()?.observe(this) { dataInfo ->
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

    override fun onRefresh() {
        // Do nothing
    }

    override fun onLoadMore() {
        getPokemonList()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}