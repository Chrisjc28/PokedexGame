package com.unomaster.pokedexgame.domain

import com.unomaster.pokedexgame.domain.models.PokemonDetailsResponse
import com.unomaster.pokedexgame.domain.network.NetworkDependencies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class PokemonUseCase(
    private val pokemonRepository: PokemonRepository = PokemonRepository(),
    scope: CoroutineScope
) {

    private val _pokemonRequest = MutableStateFlow(NetworkDependencies.baseUrl)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonRequest: StateFlow<PokemonDetailsResponse?> = _pokemonRequest.flatMapLatest {
        pokemonRepository.fetchPokemon(it)
    }.stateIn(
        scope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun restartGame(url: String) {
        _pokemonRequest.update { url }
    }

}