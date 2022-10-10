package br.com.carlosscotus.core.usecase

import br.com.carlosscotus.core.data.repository.FavoritesRepository
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.base.ResultStatus
import br.com.carlosscotus.core.usecase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoveFavoriteUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,
        val title: String,
        val imageUrl: String,
        var price: String,
        val featured: String?,
        val priceDiscount: String?,
        val discountPercentage: String,
        val hasDiscount: Boolean = false,
        val description: String = "",
        val productId: String = "",
        val releaseDate: String = ""
    )
}

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : UseCase<RemoveFavoriteUseCase.Params, Unit>(), RemoveFavoriteUseCase {

    override suspend fun doWork(params: RemoveFavoriteUseCase.Params): ResultStatus<Unit> =
        withContext(Dispatchers.IO) {
            favoritesRepository.removeFavorite(
                params.run {
                    Game(
                        id,
                        title,
                        imageUrl,
                        price,
                        featured,
                        priceDiscount,
                        discountPercentage,
                        hasDiscount,
                        description,
                        productId,
                        releaseDate
                    )
                }
            )

            ResultStatus.Success(Unit)
        }
}