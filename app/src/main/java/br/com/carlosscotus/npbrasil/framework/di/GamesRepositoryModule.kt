package br.com.carlosscotus.npbrasil.framework.di

import br.com.carlosscotus.core.data.repository.GamesRemoteDataSource
import br.com.carlosscotus.core.data.repository.GamesRepository
import br.com.carlosscotus.npbrasil.framework.network.data.GameDataWrapperResponse
import br.com.carlosscotus.npbrasil.framework.network.data.RequestResponse
import br.com.carlosscotus.npbrasil.framework.remote.RetrofitGamesDataSource
import br.com.carlosscotus.npbrasil.framework.repository.GamesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GamesRepositoryModule {

    @Binds
    fun bindGameRemoteDataSource(
        retrofitGamesDataSource: RetrofitGamesDataSource
    ): GamesRemoteDataSource<RequestResponse, GameDataWrapperResponse>

    @Binds
    fun bindGameRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository
}