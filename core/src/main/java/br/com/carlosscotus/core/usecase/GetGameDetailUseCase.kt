package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.repository.GamesRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.base.ResultStatus
import br.com.carlosscotus.core.usecase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetGameDetailUseCase {
    operator fun invoke(params: String): Flow<ResultStatus<Game>>
}

class GetGameDetailUseCaseImpl @Inject constructor(
    private val repository: GamesRepository
) : UseCase<String, Game>(), GetGameDetailUseCase {
    override suspend fun doWork(params: String) = withContext(Dispatchers.IO) {
        val request = repository.getGame(params)
        ResultStatus.Success(request)
    }
}