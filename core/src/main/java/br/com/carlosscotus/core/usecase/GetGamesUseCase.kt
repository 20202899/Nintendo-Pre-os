package br.com.carlosscotus.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.data.repository.GamesRepository
import br.com.carlosscotus.core.data.repository.StorageRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface GetGamesUseCase<R : Any> {
    operator fun invoke(params: GetGamesParams): Flow<PagingData<R>>

    data class GetGamesParams(val pagingConfig: PagingConfig)
}

class GetGamesUseCaseImpl @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val storageRepository: StorageRepository
) : PagingUseCase<GetGamesUseCase.GetGamesParams, Game>(),
    GetGamesUseCase<Game> {
    override fun createFlowObservable(params: GetGamesUseCase.GetGamesParams): Flow<PagingData<Game>> {
        val sorting = runBlocking { storageRepository.sorting.first() }
        val filter = if (sorting.isEmpty()) GameFilters.SWITCH_ONLY else
            GameFilters.fromString(sorting)

        return Pager(params.pagingConfig) {
            gamesRepository.getGames(filter)
        }.flow
    }
}