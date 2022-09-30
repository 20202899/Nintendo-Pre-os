package br.com.carlosscotus.core.data.repository

import br.com.carlosscotus.core.domain.model.Game

interface GamesRemoteDataSource<P, T> {

    suspend fun fetchGames(body: P): T

    suspend fun fetchGame(productId: String): Game
}