package br.com.carlosscotus.npbrasil.framework.repository

import androidx.paging.PagingSource
import br.com.carlosscotus.core.data.repository.GamesRemoteDataSource
import br.com.carlosscotus.core.data.repository.GamesRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.framework.network.response.GameDataWrapperResponse
import br.com.carlosscotus.npbrasil.framework.network.response.RequestResponse
import br.com.carlosscotus.npbrasil.framework.paging.GamesPagingSource
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gamesDataSource: GamesRemoteDataSource<RequestResponse, GameDataWrapperResponse>
) : GamesRepository {

    override fun getGames(): PagingSource<Int, Game> {
        return GamesPagingSource(gamesDataSource)
    }

    override suspend fun getGame(productId: String): Game {
        return  gamesDataSource.fetchGame(productId)
    }
}