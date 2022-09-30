package br.com.carlosscotus.npbrasil.framework.network.response

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

enum class GameFilters(val value: String) {
    SWITCH_ONLY("corePlatforms:Nintendo Switch"),
    SWITCH_SALES("topLevelFilters:Promoções")
}