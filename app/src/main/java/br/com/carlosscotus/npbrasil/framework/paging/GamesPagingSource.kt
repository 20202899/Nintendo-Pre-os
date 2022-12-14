package br.com.carlosscotus.npbrasil.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.data.repository.GamesRemoteDataSource
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.framework.network.data.*
import br.com.carlosscotus.npbrasil.utils.DoubleUtil
import br.com.carlosscotus.npbrasil.utils.DoubleUtil.formatCurrency

class GamesPagingSource(
    private val remoteDataSource: GamesRemoteDataSource<RequestResponse, GameDataWrapperResponse>,
    private val gameFilters: GameFilters
) : PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val page = params.key ?: 0

            val response = remoteDataSource.fetchGames(
                RequestResponse(
                    listOf(
                        RequestBody(
                            facetFilters = listOf(gameFilters.value),
                            hitsPerPage = NUMBER_PER_PAGE,
                            page = page
                        )
                    )
                )
            ).results.first()

            val nextPage = if (response.hits.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                response.hits.mapNotNull { game ->
                    game.price?.let {
                        val salePrice: Double = game.price.salePrice ?: 0.0
                        Game(
                            id = game.objectID,
                            title = game.title,
                            imageUrl = game.productImage,
                            price = game.price.regPrice.formatCurrency(),
                            featured = game.availability.first(),
                            priceDiscount = salePrice.formatCurrency(),
                            discountPercentage = DoubleUtil.formatPercentageFromSubtraction(
                                salePrice,
                                game.price.regPrice
                            ),
                            hasDiscount = game.price.salePrice != null,
                            description = game.description ?: String(),
                            productId = game.urlKey
                        )
                    }
                },
                null,
                nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

const val NUMBER_PER_PAGE = 20