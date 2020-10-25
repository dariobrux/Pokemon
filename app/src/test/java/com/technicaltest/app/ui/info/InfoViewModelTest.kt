package com.technicaltest.app.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.models.PokemonData
import com.technicaltest.app.models.sprites.OfficialArtwork
import com.technicaltest.app.models.sprites.OtherSprite
import com.technicaltest.app.models.sprites.Sprite
import com.technicaltest.app.other.Resource
import junit.framework.TestCase
import okhttp3.internal.notifyAll
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


class InfoViewModelTest : TestCase() {

    private lateinit var openMocks: AutoCloseable
    private lateinit var viewModel: InfoViewModel

    @Mock
    private lateinit var repository: InfoRepository

    public override fun setUp() {
        super.setUp()
        openMocks = MockitoAnnotations.openMocks(this)
        viewModel = InfoViewModel(repository)
    }

    override fun tearDown() {
        super.tearDown()
        openMocks.close()
    }

    @Test
    fun testNotExistingPokemon() {
        val pokemonData = PokemonData()
        val resource = Resource(Resource.Status.SUCCESS, pokemonData, null)
        val liveData = MutableLiveData(resource)
        `when`(repository.getPokemonData("", "https://pokeapi.co/api/v2/pokemon/")).thenReturn(liveData)
        val pokemonDataResult = viewModel.getPokemonData("", "https://pokeapi.co/api/v2/pokemon/")
        assertEquals(pokemonDataResult.value?.data?.name, "")
    }

    @Test
    fun testExistingPokemon() {
        val pokemonData = PokemonData().apply { name = "ditto" }
        val resource = Resource(Resource.Status.SUCCESS, pokemonData, null)
        val liveData = MutableLiveData(resource)
        `when`(repository.getPokemonData("ditto", "https://pokeapi.co/api/v2/pokemon/ditto")).thenReturn(liveData)
        val pokemonDataResult = viewModel.getPokemonData("ditto", "https://pokeapi.co/api/v2/pokemon/ditto")
        assertEquals(pokemonData.name, pokemonDataResult.value?.data?.name)
    }

    @Test
    fun testGetPictureUrl() {
        val pictureUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        val pokemonData = PokemonData().apply {
            name = "ditto"
            sprites = Sprite().apply {
                otherSprites = OtherSprite().apply {
                    officialArtwork = OfficialArtwork().apply {
                        frontDefault = pictureUrl
                    }
                }
            }
        }
        assertEquals(pictureUrl, viewModel.getPictureUrl(pokemonData))
    }
}