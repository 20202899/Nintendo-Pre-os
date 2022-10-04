package br.com.carlosscotus.npbrasil.framework.network.data

import br.com.carlosscotus.core.data.GameFilters

data class RequestResponse(
    val requests: List<RequestBody>
)

data class RequestBody(
    val indexName: String = "store_game_pt_br",
    val facets: List<String> = listOf(
        "topLevelFilters",
        "nsoFeatures",
        "corePlatforms",
        "availability",
        "genres",
        "editions",
        "franchises",
        "priceRange",
        "classindRating",
        "playerCount",
        "softwarePublisher",
        "softwareDeveloper"
    ),
    val facetFilters: List<String> = listOf(),
    val facetingAfterDistinct: Boolean = true,
    val params: String = "highlightPreTag",
    val hitsPerPage: Int = 40,
    val page: Int = 0
)