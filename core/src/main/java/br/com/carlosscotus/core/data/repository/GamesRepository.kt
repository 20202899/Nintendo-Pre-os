package br.com.carlosscotus.core.data.repository

import androidx.paging.PagingSource
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.domain.model.Game

interface GamesRepository {

    fun getGames(gameFilters: GameFilters): PagingSource<Int, Game>

    suspend fun getGame(productId: String): Game
}