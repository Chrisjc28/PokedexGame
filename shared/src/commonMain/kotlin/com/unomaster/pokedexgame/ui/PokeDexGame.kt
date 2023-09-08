package com.unomaster.pokedexgame.ui

import PokeDexGameTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.unomaster.pokedexgame.domain.PokemonUseCase

@Composable
fun PokeDexGame() {
    val scope = rememberCoroutineScope()
    val pokemonUseCase = PokemonUseCase(scope = scope)
    val pokemonDetails by pokemonUseCase.pokemonRequest.collectAsState()

    PokeDexGameTheme {
        pokemonDetails?.name?.let { Text(it) }
    }
}