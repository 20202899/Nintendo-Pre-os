package br.com.carlosscotus.npbrasil.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.carlosscotus.npbrasil.framework.db.dao.FavoriteDao
import br.com.carlosscotus.npbrasil.framework.db.entity.GameEntity

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}