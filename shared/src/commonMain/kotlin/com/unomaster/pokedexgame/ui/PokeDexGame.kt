package com.unomaster.pokedexgame.ui

import PokeDexGameTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.unomaster.pokedexgame.domain.PokemonUseCase
import com.unomaster.pokedexgame.domain.Result
import com.unomaster.pokedexgame.domain.models.CombinedPokemonResponse
import com.unomaster.pokedexgame.domain.network.NetworkDependencies

@Composable
fun PokeDexGame() {
    val scope = rememberCoroutineScope()
    val pokemonUseCase = PokemonUseCase(scope = scope)
    val combinedPokemonResponse by pokemonUseCase.pokemonRequest.collectAsState()

    LaunchedEffect(Unit) {
        pokemonUseCase.startGame(NetworkDependencies.baseUrl)
    }

    PokeDexGameTheme {
        when (val result = combinedPokemonResponse) {
            is Result.Success<CombinedPokemonResponse> -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val pokemonBitmap by remember { result.data.pokemonBitmap }
                    PokemonImage(
                        pokemonBitmap,
                        pokemonUseCase
                    )

                    RestartGame(
                        pokemonUseCase,
                        result.data.pokemonApiResponse.next,
                    )

                    val multipleChoiceList by remember { result.data.multipleChoiceList }
                    val isWinner = remember { pokemonUseCase._winner }

                    MultipleChoiceContainer(
                        multipleChoiceList,
                        isWinner,
                    ) { selectPokemonName ->
                        val pokemonNameFromApi = result.data.pokemonDetailsResponse.name.capitalize(Locale.current)
                        pokemonUseCase.handleMultipleItemChoiceState(
                            selectPokemonName, pokemonNameFromApi
                        )
                    }
                }
            }

            is Result.Error -> {
                Text(result.error)
            }

            is Result.Loading -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(120.dp),
                        color = Color.Blue
                    )
                }

            }
        }
    }
}