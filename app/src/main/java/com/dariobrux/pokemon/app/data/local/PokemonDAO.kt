package com.dariobrux.pokemon.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dariobrux.pokemon.app.data.local.model.PokemonEntity

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 * This interface is the DAO.
 * It's responsible for defining the methods that access the database.
 *
 */

@Dao
interface PokemonDAO {

    /**
     * Get the list of all the Pokemon stored.
     * @return the list with all Pokemon.
     */
    @Query("Select * from pokemon limit :limit offset :offset")
    suspend fun getPokemonList(offset: Int, limit: Int = 20): List<PokemonEntity>?

    /**
     * Add a Pokemon in the database.
     * @param pokemon: the [PokemonEntity] to insert.
     * Use the replacing strategy to override each existing item.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)
}