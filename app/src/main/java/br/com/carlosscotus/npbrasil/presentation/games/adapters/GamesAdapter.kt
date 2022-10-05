package br.com.carlosscotus.npbrasil.presentation.games.adapters

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader

class GamesAdapter(
    private val imageLoader: ImageLoader,
    private val click: (game: Game?, view: View) -> Unit
) : PagingDataAdapter<Game, GameViewHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder =
        GameViewHolder.create(parent, imageLoader, click)

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Game>() {

            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }
        }
    }
}