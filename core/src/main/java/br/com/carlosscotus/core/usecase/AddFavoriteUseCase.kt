package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.repository.FavoritesRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.base.ResultStatus
import br.com.carlosscotus.core.usecase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddFavoriteUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,
        val title: String,
        val imageUrl: String,
        val price: String,
        val featured: String?,
        var priceDiscount: String?,
        var discountPercentage: String,
        var hasDiscount: Boolean = false
    )
}

class AddFavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoritesRepository
) : UseCase<AddFavoriteUseCase.Params, Unit>(), AddFavoriteUseCase {
    override suspend fun doWork(params: AddFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(Dispatchers.IO) {
            repository.saveFavorite(params.run {
                Game(
                    id,
                    title,
                    imageUrl,
                    price,
                    featured,
                    priceDiscount,
                    discountPercentage,
                    hasDiscount
                )
            })
            ResultStatus.Success(Unit)
        }
    }
}