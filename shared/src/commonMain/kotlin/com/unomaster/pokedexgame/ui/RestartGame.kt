package com.unomaster.pokedexgame.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unomaster.pokedexgame.domain.PokemonUseCase

@Composable
fun RestartGame(
    pokemonUseCase: PokemonUseCase,
    pokemonUrl: String,
) {
    val isWinner = remember { pokemonUseCase._winner }
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    AnimatedVisibility(isWinner.collectAsState().value) {
        Column {
            Text(
                text = "Congratulation you got it right!",
                modifier = Modifier.padding(vertical = 12.dp),
                color = textColor
            )
            Button(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    pokemonUseCase.restartGame(
                        pokemonUrl
                    )
                }
            ) {
                Text(
                    "Play again?",
                    color = Color.White
                )
            }
        }
    }
}