package br.com.carlosscotus.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.carlosscotus.core.data.repository.GamesRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetGamesUseCase<R : Any> {
    operator fun invoke(params: GetGamesParams): Flow<PagingData<R>>

    data class GetGamesParams(val pagingConfig: PagingConfig)
}

class GetGamesUseCaseImpl @Inject constructor(
    private val gamesRepository: GamesRepository
) : PagingUseCase<GetGamesUseCase.GetGamesParams, Game>(),
    GetGamesUseCase<Game> {
    override fun createFlowObservable(params: GetGamesUseCase.GetGamesParams) =
        Pager(params.pagingConfig) {
            gamesRepository.getGames()
        }.flow
}