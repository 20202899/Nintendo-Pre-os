package br.com.carlosscotus.core.domain.model

data class Game(
    val id: String,
    val title: String,
    val imageUrl: String,
    var price: String,
    val featured: String?,
    val priceDiscount: String?,
    val discountPercentage: String,
    val hasDiscount: Boolean = false,
    val description: String = "",
    val productId: String = ""
)