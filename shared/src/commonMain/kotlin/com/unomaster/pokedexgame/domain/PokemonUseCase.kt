package com.unomaster.pokedexgame.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.unomaster.pokedexgame.domain.models.CombinedPokemonResponse
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
    private val _pokemonRequest = MutableStateFlow<String?>(null)

    val _winner = MutableStateFlow<Boolean>(false)
    val _overlay = MutableStateFlow<ColorFilter?>(ColorFilter.tint(Color.LightGray))

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonRequest: StateFlow<Result<CombinedPokemonResponse, String>> =
        _pokemonRequest.flatMapLatest {
            if (it != null) {
                pokemonRepository.fetchPokemon(it)
            } else {
                throw NullPointerException()
            }
        }.stateIn(
            scope,
            SharingStarted.WhileSubscribed(5000),
            Result.Loading
        )

    fun startGame(url: String) {
        _pokemonRequest.update { url }
    }

    fun restartGame(url: String) {
        _pokemonRequest.update { url }
        _winner.update { false }
        _overlay.update { ColorFilter.tint(Color.LightGray) }
    }

    fun handleMultipleItemChoiceState(selectPokemonName: String, pokemonNameFromApi: String) {
        if (selectPokemonName == pokemonNameFromApi) {
            _winner.update { true }
            _overlay.update { null }
        } else {
            _winner.update { false }
            _overlay.update { ColorFilter.tint(Color.LightGray) }
        }
    }

}