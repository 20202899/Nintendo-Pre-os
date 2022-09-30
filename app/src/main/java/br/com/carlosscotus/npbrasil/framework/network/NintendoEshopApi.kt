package br.com.carlosscotus.npbrasil.framework.network

import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.framework.network.response.GameDataWrapperResponse
import br.com.carlosscotus.npbrasil.framework.network.response.GameDetailDataWrapperResponse
import br.com.carlosscotus.npbrasil.framework.network.response.RequestResponse
import retrofit2.http.*

interface NintendoEshopApi {

    @Headers(
        "x-algolia-application-id: ${BuildConfig.API_ID}",
        "x-algolia-api-key: ${BuildConfig.API_KEY}"
    )
    @POST("queries/")
    suspend fun loadGames(@Body request: RequestResponse): GameDataWrapperResponse

    @GET
    suspend fun loadGame(@Url url: String): GameDetailDataWrapperResponse
}