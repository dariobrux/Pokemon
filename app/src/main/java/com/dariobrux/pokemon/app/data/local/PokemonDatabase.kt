package com.dariobrux.pokemon.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dariobrux.pokemon.app.data.local.converter.ImageConverter
import com.dariobrux.pokemon.app.data.local.converter.StatsConverter
import com.dariobrux.pokemon.app.data.local.converter.TypeConverter
import com.dariobrux.pokemon.app.data.local.model.ImageEntity
import com.dariobrux.pokemon.app.data.local.model.PokemonEntity
import com.dariobrux.pokemon.app.data.local.model.StatsEntity
import com.dariobrux.pokemon.app.data.local.model.TypeEntity

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 * This class is the representation of the database.
 *
 */

@Database(
    entities = [
        PokemonEntity::class,
        StatsEntity::class,
        TypeEntity::class,
        ImageEntity::class
    ],
    version = 1
)
@TypeConverters(
    TypeConverter::class,
    StatsConverter::class,
    ImageConverter::class
)
abstract class PokemonDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "pokemon.db"

        private var instance: PokemonDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PokemonDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, PokemonDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun pokemonDao(): PokemonDAO
}