package br.com.carlosscotus.npbrasil.framework.di

import br.com.carlosscotus.core.data.repository.FavoritesLocalDataSource
import br.com.carlosscotus.core.data.repository.FavoritesRepository
import br.com.carlosscotus.npbrasil.framework.local.RoomFavoritesDataSource
import br.com.carlosscotus.npbrasil.framework.repository.FavoritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    fun bindFavoritesLocalDataSource(
        favoritesLocalDataSource: RoomFavoritesDataSource
    ): FavoritesLocalDataSource

    @Binds
    fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository
}