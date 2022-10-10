package br.com.carlosscotus.npbrasil.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.databinding.ItemGameBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import br.com.carlosscotus.npbrasil.presentation.common.GenericViewHolder

class FavoriteViewHolder private constructor(
    binding: ItemGameBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<FavoriteUI>(binding) {

    private val imageGame = binding.imageGame
    private val title = binding.title
    private val subtitle = binding.subtitle

    override fun bind(data: FavoriteUI) {
        data.let {
            imageLoader.load(imageGame, "${BuildConfig.IMAGE_LOADER_URL.replace(
                "c_scale,w_300",
                "c_scale,w_600"
            )}${it.imageUrl}")
            title.text = data.title
            subtitle.text = data.price
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