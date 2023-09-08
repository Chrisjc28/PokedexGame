package com.unomaster.pokedexgame.domain

sealed interface Result<out Data, out Error> {
    data class Success<D>(val data: D):Result<D, Nothing>
    data object Loading:Result<Nothing, Nothing>
    data class Error<E>(val error: E):Result<Nothing, E>
}
