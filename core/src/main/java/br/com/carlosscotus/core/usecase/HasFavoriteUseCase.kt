package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.repository.FavoritesRepository
import br.com.carlosscotus.core.usecase.base.ResultStatus
import br.com.carlosscotus.core.usecase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HasFavoriteUseCase {

    operator fun invoke(params: String): Flow<ResultStatus<Boolean>>
}

class HasFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : UseCase<String, Boolean>(), HasFavoriteUseCase {

    override suspend fun doWork(params: String): ResultStatus<Boolean> =
        withContext(Dispatchers.IO) {
            val isFavorite = favoritesRepository.hasFavorite(params)
            ResultStatus.Success(isFavorite)
        }
}