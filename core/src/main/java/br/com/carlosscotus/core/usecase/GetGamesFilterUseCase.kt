package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.data.repository.StorageRepository
import br.com.carlosscotus.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetGamesFilterUseCase {
    suspend operator fun invoke(params: Unit): Flow<GameFilters>
}

class GetGamesFilterUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
) : FlowUseCase<Unit, GameFilters>(), GetGamesFilterUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<GameFilters> =
        storageRepository.sorting.map {
            if (it.isEmpty()) GameFilters.SWITCH_ONLY else GameFilters.fromString(it)
        }
}