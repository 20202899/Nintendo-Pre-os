package br.com.carlosscotus.npbrasil.framework.repository

import br.com.carlosscotus.core.data.repository.FavoritesLocalDataSource
import br.com.carlosscotus.core.data.repository.FavoritesRepository
import br.com.carlosscotus.core.domain.model.Game
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource
) : FavoritesRepository {
    override fun getFavorites(): Flow<List<Game>> {
        return favoritesLocalDataSource.fetchFavorites()
    }

    override suspend fun saveFavorite(game: Game) {
        favoritesLocalDataSource.saveFavorite(game)
    }

    override suspend fun removeFavorite(game: Game) {
        favoritesLocalDataSource.removeFavorite(game)
    }
}