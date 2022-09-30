package br.com.carlosscotus.npbrasil.framework.remote

import br.com.carlosscotus.core.data.repository.GamesRemoteDataSource
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.framework.network.NintendoEshopApi
import br.com.carlosscotus.npbrasil.framework.network.response.GameDataWrapperResponse
import br.com.carlosscotus.npbrasil.framework.network.response.RequestResponse
import br.com.carlosscotus.npbrasil.utils.DoubleUtil.formatCurrency
import br.com.carlosscotus.npbrasil.utils.DoubleUtil.formatPercentageCurrency
import javax.inject.Inject

class RetrofitGamesDataSource @Inject constructor(
    private val nintendoEshopApi: NintendoEshopApi
) : GamesRemoteDataSource<RequestResponse, GameDataWrapperResponse> {

    override suspend fun fetchGames(body: RequestResponse): GameDataWrapperResponse {
        return nintendoEshopApi.loadGames(body)
    }

    override suspend fun fetchGame(productId: String) =
        nintendoEshopApi.loadGame(String.format(
            BuildConfig.PRODUCT_URL,
            productId,
            productId
        )).pageProps.product.let {
            val prices = it.prices

            Game(
                it.id,
                it.name,
                it.productImage.publicId,
                if (prices.minimum.discounted)
                    prices.maximum?.regularPrice?.formatCurrency() ?: String()
                else prices.minimum.regularPrice.formatCurrency(),
                it.availability.first(),
                prices.minimum.regularPrice.formatCurrency(),
                prices.minimum.amountOff.formatPercentageCurrency(),
                prices.minimum.discounted,
                it.description,
                productId
            )
        }
}