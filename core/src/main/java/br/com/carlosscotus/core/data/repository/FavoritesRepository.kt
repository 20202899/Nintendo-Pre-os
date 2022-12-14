package br.com.carlosscotus.core.data.repository

import br.com.carlosscotus.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavorites(): Flow<List<Game>>

    suspend fun hasFavorite(gameId: String): Boolean

    suspend fun saveFavorite(game: Game)

    suspend fun removeFavorite(game: Game)
}