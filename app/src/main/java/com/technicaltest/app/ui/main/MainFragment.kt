package com.technicaltest.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicaltest.app.R
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.ui.adapters.PokemonAdapter
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private val pokemonList = mutableListOf<Pokemon>()
    private val adapter : PokemonAdapter = PokemonAdapter(pokemonList)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.init()
        viewModel.getPokemonRepository()?.observe(this) { dataInfo ->
            Log.d("Pokemon", dataInfo?.pokemonList?.firstOrNull()?.name ?: "")
            pokemonList.addAll(dataInfo.pokemonList?: emptyList())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }
}