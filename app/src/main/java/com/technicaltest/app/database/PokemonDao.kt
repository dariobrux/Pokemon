package com.technicaltest.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technicaltest.app.models.Pokemon

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 * This interface is the DAO.
 * It's responsible for defining the methods that access the database.
 *
 */

@Dao
interface PokemonDao {

    /**
     * Get the list of all the pokemon stored.
     * @return the list with all pokemon.
     */
    @Query("Select * from pokemon limit :limit offset :offset")
    fun getPokemonList(offset: Int, limit: Int): List<Pokemon>

    /**
     * Add a pokemon in the database.
     * @param pokemon: the [Pokemon] object to insert.
     * Use the replacing strategy to override each existing item.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonList(pokemonList: List<Pokemon>)
}