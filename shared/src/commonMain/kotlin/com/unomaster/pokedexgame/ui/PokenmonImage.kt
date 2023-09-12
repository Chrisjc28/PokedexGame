package com.unomaster.pokedexgame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.unomaster.pokedexgame.domain.PokemonUseCase

@Composable
fun PokemonImage(
    pokemonBitmap: ImageBitmap?,
    pokemonUseCase: PokemonUseCase
) {
    val colorFilter = remember { pokemonUseCase._overlay }

    pokemonBitmap?.let {
        Image(
            it,
            null,
            modifier = Modifier.size(300.dp),
            colorFilter = colorFilter.collectAsState().value
        )
    }
}
