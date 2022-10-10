package br.com.carlosscotus.npbrasil.framework.db.dao

import androidx.room.*
import br.com.carlosscotus.npbrasil.framework.db.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM games")
    fun getFavorites(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun hasFavorite(gameId: String): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(gameEntity: GameEntity)

    @Delete
    suspend fun deleteFavorite(gameEntity: GameEntity)
}