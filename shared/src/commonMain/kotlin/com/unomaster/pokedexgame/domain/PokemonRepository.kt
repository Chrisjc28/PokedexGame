package com.unomaster.pokedexgame.domain

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.unomaster.pokedexgame.domain.models.CombinedPokemonResponse
import com.unomaster.pokedexgame.domain.models.PokemonApiResponse
import com.unomaster.pokedexgame.domain.models.PokemonDetailsResponse
import com.unomaster.pokedexgame.domain.network.NetworkDependencies
import com.unomaster.pokedexgame.domain.network.PokemonService
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlin.random.Random

class PokemonRepository(
    private val pokemonService: PokemonService = PokemonService(),
    private val customJson: Json = NetworkDependencies.provideCustomJson()
) {
    suspend fun fetchPokemon(url: String) = flow<Result<CombinedPokemonResponse, String>> {
        emit(Result.Loading)
        val pokemonListRequest = pokemonService.getPokemonList(url)
        if (pokemonListRequest.status.value == 200) {
            val body = pokemonListRequest.bodyAsText()
            val pokemonApiResponse = customJson.decodeFromString<PokemonApiResponse>(body)

            val randomPosition = Random.nextInt(0, 20)

            val pokemonDetailsUrl = pokemonApiResponse.results[randomPosition].url
            val pokemonDetailsName = pokemonApiResponse.results[randomPosition].name

            val multipleChoiceList = getMultipleChoiceList(pokemonApiResponse, pokemonDetailsName)

            val pokemonDetailsRequest = pokemonService.getPokemonDetails(pokemonDetailsUrl)

            if (pokemonDetailsRequest.status.value == 200) {
                val pokemonDetailsBody = pokemonDetailsRequest.bodyAsText()
                val pokemonDetailsResponse =
                    customJson.decodeFromString<PokemonDetailsResponse>(pokemonDetailsBody)

                val combinedPokemonResponse = combinedPokemonResponse(
                    pokemonApiResponse,
                    pokemonDetailsResponse,
                    multipleChoiceList
                )

                emit(Result.Success(combinedPokemonResponse))
            } else {
                emit(Result.Error(pokemonDetailsRequest.status.description))
            }
        } else {
            emit(Result.Error(pokemonListRequest.status.description))
        }
    }.catch {
        emit(Result.Error(it.message.orEmpty()))
    }

    private suspend fun combinedPokemonResponse(
        pokemonApiResponse: PokemonApiResponse,
        pokemonDetailsResponse: PokemonDetailsResponse,
        multipleChoiceList: List<String>
    ) = CombinedPokemonResponse(
        pokemonApiResponse,
        pokemonDetailsResponse,
        pokemonBitmap = mutableStateOf(
            loadImageFromUrl(pokemonDetailsResponse.sprites.other.officialArtwork.frontDefault),
        ),
        multipleChoiceList = mutableStateOf(
            multipleChoiceList
        )
    )

    private fun getMultipleChoiceList(
        pokemonApiResponse: PokemonApiResponse,
        pokemonDetailsName: String
    ) = pokemonApiResponse.results.filter {
        it.name == pokemonDetailsName
    }.map { it.name.capitalize(Locale.current) }.distinct() + pokemonApiResponse.results.subList(0, 4).map {
        it.name.capitalize(Locale.current)
    }.distinct()
}