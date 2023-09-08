package com.unomaster.pokedexgame.domain.network

import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
class PokemonService {
    suspend fun getPokemonList(url: String): HttpResponse {
        return NetworkDependencies.client.get(url)
    }

    suspend fun getPokemonDetails(url: String): HttpResponse {
        return NetworkDependencies.client.get(url)
    }
}