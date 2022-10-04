package br.com.carlosscotus.core.data

enum class GameFilters(val value: String) {
    SWITCH_ONLY("corePlatforms:Nintendo Switch"),
    SWITCH_SALES("topLevelFilters:Promoções");

    companion object {
        fun fromString(value: String) = values().first { it.value == value }
    }
}