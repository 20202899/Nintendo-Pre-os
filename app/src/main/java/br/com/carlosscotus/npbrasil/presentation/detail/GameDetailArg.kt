package br.com.carlosscotus.npbrasil.presentation.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class GameDetailArg(
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
    val releaseDate: String = ""
) : Parcelable