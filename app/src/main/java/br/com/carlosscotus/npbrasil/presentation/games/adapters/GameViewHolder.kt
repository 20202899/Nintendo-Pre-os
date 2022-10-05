package br.com.carlosscotus.npbrasil.presentation.games.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.databinding.ItemGameBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader

class GameViewHolder private constructor(
    binding: ItemGameBinding,
    private val imageLoader: ImageLoader,
    private val click: (game: Game?, view: View) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val imageGame = binding.imageGame
    private val title = binding.title
    private val subtitle = binding.subtitle
    private val discount = binding.subtitleDiscount
    private val chipDiscount = binding.chipDiscount
    private val feature = binding.feature

    fun bind(game: Game?) {
        game?.let {
            imageLoader.load(imageGame, "${BuildConfig.IMAGE_LOADER_URL}${it.imageUrl}")
            title.text = game.title
            subtitle.text = game.price
            discount.text = game.priceDiscount
            chipDiscount.text = game.discountPercentage
            hideDiscountInfo(game.hasDiscount)

            imageGame.transitionName = game.id

            itemView.setOnClickListener { _ ->
                click(it, imageGame)
            }
        }
    }

    private fun hideDiscountInfo(hasDiscount: Boolean) {
        discount.visibility = if (!hasDiscount) View.GONE else View.VISIBLE
        chipDiscount.visibility = if (!hasDiscount) View.GONE else View.VISIBLE
        subtitle.paintFlags =
            if (hasDiscount) Paint.STRIKE_THRU_TEXT_FLAG else Paint.ANTI_ALIAS_FLAG
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            click: (game: Game?, view: View) -> Unit
        ) =
            GameViewHolder(
                ItemGameBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                imageLoader,
                click
            )
    }
}