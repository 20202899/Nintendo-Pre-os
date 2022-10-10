package br.com.carlosscotus.npbrasil.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.databinding.ItemGameBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader

class FavoritesAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<Game, FavoriteViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder.create(
            parent,
            imageLoader
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class FavoriteViewHolder private constructor(
    binding: ItemGameBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    private val imageGame = binding.imageGame
    private val title = binding.title
    private val subtitle = binding.subtitle

    fun bind(game: Game?) {
        game?.let {
            imageLoader.load(imageGame, "${BuildConfig.IMAGE_LOADER_URL.replace(
                "c_scale,w_300",
                "c_scale,w_600"
            )}${it.imageUrl}")
            title.text = game.title
            subtitle.text = game.price

            itemView.setOnClickListener { _ ->

            }
        }
    }

    companion object {
        fun create(view: ViewGroup, imageLoader: ImageLoader): FavoriteViewHolder {
            return FavoriteViewHolder(
                ItemGameBinding.inflate(
                    LayoutInflater.from(view.context),
                    view,
                    false
                ),
                imageLoader
            )
        }
    }
}