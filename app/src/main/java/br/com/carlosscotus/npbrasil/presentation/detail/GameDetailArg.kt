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
    val productId: String
) : Parcelable