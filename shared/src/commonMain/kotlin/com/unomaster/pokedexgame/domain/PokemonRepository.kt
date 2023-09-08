package com.unomaster.pokedexgame.domain

import androidx.compose.material.RadioButton
import com.unomaster.pokedexgame.domain.models.PokemonApiResponse
import com.unomaster.pokedexgame.domain.models.PokemonDetailsResponse
import com.unomaster.pokedexgame.domain.network.NetworkDependencies
import com.unomaster.pokedexgame.domain.network.PokemonService
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.random.Random

class PokemonRepository(
    private val pokemonService: PokemonService = PokemonService(),
    private val customJson: Json = NetworkDependencies.provideCustomJson()
) {
    suspend fun fetchPokemon(url: String) = flow<PokemonDetailsResponse> {
        val pokemonListRequest = pokemonService.getPokemonList(url)
        if (pokemonListRequest.status.value == 200) {
            val body = pokemonListRequest.bodyAsText()
            val pokemonApiResponse = customJson.decodeFromString<PokemonApiResponse>(body)

            val randomPosition = Random.nextInt(0, 20)

            val pokemonDetailsUrl = pokemonApiResponse.results[randomPosition].url
            val pokemonDetailsRequest = pokemonService.getPokemonDetails(pokemonDetailsUrl)

            if (pokemonDetailsRequest.status.value == 200) {
                val pokemonDetailsBody = pokemonDetailsRequest.bodyAsText()
                val pokemonDetailsResponse =
                    customJson.decodeFromString<PokemonDetailsResponse>(pokemonDetailsBody)
                emit(pokemonDetailsResponse)
            }
        }
    }
}