package br.com.carlosscotus.npbrasil.presentation.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.carlosscotus.npbrasil.databinding.ItemGameRefreshErrorBinding

class GamesLoadMoreStateViewHolder(
    itemGameRefreshErrorBinding: ItemGameRefreshErrorBinding,
    val refresh: () -> Unit
) : RecyclerView.ViewHolder(itemGameRefreshErrorBinding.root) {

    private val progress = itemGameRefreshErrorBinding.progress
    private val textError = itemGameRefreshErrorBinding.textError.apply {
        setOnClickListener { refresh() }
    }

    fun bind(loadState: LoadState) {
        progress.isVisible = loadState is LoadState.Loading
        textError.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(
            parent: ViewGroup,
            refresh: () -> Unit
        ) = GamesLoadMoreStateViewHolder(
            ItemGameRefreshErrorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            refresh
        )
    }
}