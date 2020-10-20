package com.technicaltest.app.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technicaltest.app.R
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.adapters.PokemonAdapter
import com.technicaltest.app.ui.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private lateinit var adapter : PokemonAdapter

    private val pokemonList = mutableListOf<Pokemon>()

    private lateinit var viewModel: MainViewModel

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
        viewModel.getPokemonRepository()?.observe(this) { dataInfo ->
            pokemonList.addAll(dataInfo.pokemonList?: emptyList())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler?.let {
            it.addItemDecoration(VerticalSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_space)))
            it.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            it.adapter = adapter
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}