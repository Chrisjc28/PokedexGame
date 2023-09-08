package com.unomaster.pokedexgame.domain

import androidx.compose.ui.graphics.ImageBitmap

expect suspend fun loadImageFromUrl(url: String): ImageBitmap