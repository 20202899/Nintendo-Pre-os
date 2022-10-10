package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.repository.FavoritesRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.base.FlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetFavoritesUseCase {

    suspend operator fun invoke(params: Unit = Unit): Flow<List<Game>>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : FlowUseCase<Unit, List<Game>>(), GetFavoritesUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<List<Game>> =
        withContext(Dispatchers.IO) {
            favoritesRepository.getFavorites()
        }
}