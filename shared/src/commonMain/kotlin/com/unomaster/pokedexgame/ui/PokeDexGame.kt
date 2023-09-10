package com.unomaster.pokedexgame.ui

import PokeDexGameTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unomaster.pokedexgame.domain.PokemonUseCase
import com.unomaster.pokedexgame.domain.Result
import com.unomaster.pokedexgame.domain.models.CombinedPokemonResponse
import com.unomaster.pokedexgame.domain.network.NetworkDependencies

@Composable
fun PokeDexGame() {
    val scope = rememberCoroutineScope()
    val pokemonUseCase = PokemonUseCase(scope = scope)
    val combinedPokemonResponse by pokemonUseCase.pokemonRequest.collectAsState()
    val backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.White
    val progressColor = if (isSystemInDarkTheme()) Color(0xFF67B7D1) else Color.Blue
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    LaunchedEffect(Unit) {
        pokemonUseCase.startGame(NetworkDependencies.baseUrl)
    }

    PokeDexGameTheme {
        when (val result = combinedPokemonResponse) {
            is Result.Success<CombinedPokemonResponse> -> {
                val isWinner = remember { pokemonUseCase._winner }

                Column(
                    Modifier.fillMaxSize().background(backgroundColor),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AnimatedVisibility(!isWinner.collectAsState().value) {
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Who's that pokemon?",
                                fontSize = 36.sp,
                                color = textColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

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

                    MultipleChoiceContainer(
                        multipleChoiceList,
                        isWinner,
                    ) { selectPokemonName ->
                        val pokemonNameFromApi =
                            result.data.pokemonDetailsResponse.name.capitalize(Locale.current)
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
                    Modifier.fillMaxSize().background(backgroundColor),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(120.dp),
                        color = progressColor
                    )
                }

            }
        }
    }
}
