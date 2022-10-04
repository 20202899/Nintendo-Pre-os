package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.data.repository.StorageRepository
import br.com.carlosscotus.core.usecase.base.FlowUseCase
import br.com.carlosscotus.core.usecase.base.ResultStatus
import br.com.carlosscotus.core.usecase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveGamesFilterUseCase {

    operator fun invoke(params: GameFilters): Flow<ResultStatus<Unit>>
}

class SaveGamesFilterUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
) : UseCase<GameFilters, Unit>(), SaveGamesFilterUseCase {

    override suspend fun doWork(params: GameFilters): ResultStatus<Unit> =
        withContext(Dispatchers.IO) {
            storageRepository.saveSorting(params.value)
            ResultStatus.Success(Unit)
        }
}