package br.com.carlosscotus.npbrasil.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.carlosscotus.core.domain.model.Game

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: String,
    val title: String,
    val imageUrl: String,
    var price: String,
    val featured: String?,
    var priceDiscount: String?,
    var discountPercentage: String,
    var hasDiscount: Boolean = false
)

fun List<GameEntity>.toGames() = map {
    Game(
        it.id,
        it.title,
        it.imageUrl,
        it.price,
        it.featured,
        it.priceDiscount,
        it.discountPercentage,
        it.hasDiscount
    )
}
