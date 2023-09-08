package com.unomaster.pokedexgame.domain

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.unomaster.pokedexgame.domain.network.NetworkDependencies
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes


actual suspend fun loadImageFromUrl(url: String): ImageBitmap {
    val image = BitmapFactory.decodeStream(
        NetworkDependencies.client.get(url).readBytes().inputStream()
    )
    return image.asImageBitmap()
}