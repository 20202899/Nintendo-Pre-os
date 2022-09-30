package br.com.carlosscotus.npbrasil.presentation.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.AddFavoriteUseCase
import br.com.carlosscotus.core.usecase.GetGamesUseCase
import br.com.carlosscotus.npbrasil.framework.paging.GamesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase<Game>
) : ViewModel() {

    fun gamesPaginate(): Flow<PagingData<Game>> = getGamesUseCase(
        GetGamesUseCase.GetGamesParams(
            pagingConfig = getPagingConfig()
        )
    ).cachedIn(viewModelScope)

    private fun getPagingConfig() = PagingConfig(
        pageSize = GamesPagingSource.NUMBER_PER_PAGE
    )
}