package br.com.carlosscotus.npbrasil.presentation.favorites

import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.presentation.common.ListItem

data class FavoriteUI(
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
    val releaseDate: String = "",
    override val key: String = id
) : ListItem

fun Game.toFavoriteUI(): FavoriteUI {
    return run {
        FavoriteUI(
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
}
