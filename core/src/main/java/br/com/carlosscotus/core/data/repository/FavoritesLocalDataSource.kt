package br.com.carlosscotus.core.data.repository

import br.com.carlosscotus.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {

    fun fetchFavorites(): Flow<List<Game>>

    suspend fun saveFavorite(game: Game)

    suspend fun removeFavorite(game: Game)
}