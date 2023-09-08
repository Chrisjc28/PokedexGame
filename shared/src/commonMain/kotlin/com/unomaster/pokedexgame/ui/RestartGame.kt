package com.unomaster.pokedexgame.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    AnimatedVisibility(isWinner.collectAsState().value) {
        Column {
            Text(
                text = "Congratulation you got it right!",
                modifier = Modifier.padding(vertical = 12.dp),
                color = textColor
            )
            Button(
                modifier = Modifier.padding(vertical = 12.dp),
                onClick = {
                    pokemonUseCase.restartGame(
                        pokemonUrl
                    )
                }
            ) {
                Text("Click me to go again :)!!")
            }
        }
    }
}