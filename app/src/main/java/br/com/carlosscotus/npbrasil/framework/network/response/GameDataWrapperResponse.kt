package br.com.carlosscotus.npbrasil.framework.network.response

data class GameDataWrapperResponse(
    val results: List<GameDataResultResponse>
)

data class GameDataResultResponse (
    val nbHits: Int,
    val page: Int,
    val nbPages: Int,
    val hitsPerPage: Int,
    val hits: List<GameDataResponse>,
    val facets: Facets
)

data class Facets (
    val genres: Map<String, String>,
    val availability: Map<String, String>,
    val softwareDeveloper: Map<String, String>
)

data class GameDataResponse(
    val availability: List<String>,
    val classindDescriptors: Any?,
    val classindRating: String,
    val corePlatforms: List<String>,
    val demoNsuid: Any?,
    val editions: List<String>,
    val esrbDescriptors: List<String>,
    val esrbRating: String,
    val genres: List<String>,
    val nsoFeatures: List<String>,
    val playModes: List<String>,
    val playerCount: String,
    val smecDescriptors: List<String>,
    val smecRating: String,
    val softwareDeveloper: String,
    val softwarePublisher: String,
    val createdAt: String,
    val collectionPriceRange: String,
    val description: String,
    val exclusive: Boolean,
    val featuredProduct: Boolean,
    val nsuid: String,
    val platinumPoints: Any?,
    val platform: String,
    val platformCode: String,
    val price: Price?,
    val priceRange: String,
    val productImage: String,
    val franchises: List<Any>,
    val title: String,
    val releaseDateDisplay: Any?,
    val sku: String,
    val updatedAt: String,
    val url: String,
    val urlKey: String,
    val visibleInSearch: Boolean,
    val topLevelCategory: String,
    val topLevelCategoryCode: String,
    val topLevelFilters: List<Any>,
    val type: String,
    val dlcType: Any?,
    val contentRatingCode: String,
    val hasDlc: Boolean,
    val objectID: String
)

data class Price(
    val regPrice: Double,
    val salePrice: Double?,
    val finalPrice: Double
)