package br.com.carlosscotus.npbrasil.framework.local

import br.com.carlosscotus.core.data.repository.FavoritesLocalDataSource
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.framework.db.dao.FavoriteDao
import br.com.carlosscotus.npbrasil.framework.db.entity.GameEntity
import br.com.carlosscotus.npbrasil.framework.db.entity.toGames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoritesLocalDataSource {

    override fun fetchFavorites(): Flow<List<Game>> {
        return favoriteDao.getFavorites().map { it.toGames() }
    }

    override suspend fun hasFavorite(gameId: String): Boolean {
        return favoriteDao.hasFavorite(gameId) != null
    }

    override suspend fun saveFavorite(game: Game) {
        favoriteDao.insertFavorite(game.toEntity())
    }

    override suspend fun removeFavorite(game: Game) {
        favoriteDao.deleteFavorite(game.toEntity())
    }

    private fun Game.toEntity() = GameEntity(
        id,
        title,
        imageUrl,
        price,
        featured,
        priceDiscount,
        discountPercentage,
        hasDiscount,
        description,
        productId,
        releaseDate
    )
}