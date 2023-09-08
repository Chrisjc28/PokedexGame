package com.unomaster.pokedexgame.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.unomaster.pokedexgame.domain.network.NetworkDependencies
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import org.jetbrains.skia.Image

actual suspend fun loadImageFromUrl(url: String): ImageBitmap {
    val image = NetworkDependencies.client.get(url).readBytes()
    return Image.makeFromEncoded(image).toComposeImageBitmap()
}