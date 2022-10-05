package br.com.carlosscotus.npbrasil.presentation.games.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GamesLoadMoreStateAdapter(
    private val refresh: () -> Unit
) : LoadStateAdapter<GamesLoadMoreStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = GamesLoadMoreStateViewHolder.create(parent, refresh)

    override fun onBindViewHolder(holder: GamesLoadMoreStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)
}