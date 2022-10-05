package br.com.carlosscotus.core.data

enum class GameFilters(val value: String) {
    SWITCH_ONLY("corePlatforms:Nintendo Switch"),
    SWITCH_SALES("topLevelFilters:Promoções"),
    SWITCH_COMING_SOON("availability:Em breve"),
    SWItCH_NEWS_RELEASE("availability:Novos lançamentos");

    companion object {
        fun fromString(value: String) = values().first { it.value == value }
    }
}