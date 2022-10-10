package br.com.carlosscotus.npbrasil.presentation.games

import androidx.lifecycle.*
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.GetGamesFilterUseCase
import br.com.carlosscotus.core.usecase.GetGamesUseCase
import br.com.carlosscotus.core.usecase.SaveGamesFilterUseCase
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import br.com.carlosscotus.npbrasil.framework.paging.NUMBER_PER_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase<Game>,
    private val getGamesFilterUseCase: GetGamesFilterUseCase,
    saveGamesFilterUseCase: SaveGamesFilterUseCase
) : ViewModel() {

    val gameSaveFilterActionUIStateLiveData = GameSaveFilterActionUIStateLiveData(
        saveGamesFilterUseCase,
        Dispatchers.Main
    )

    private val restoredFilter: GameFilters
        get() = runBlocking { getGamesFilterUseCase(Unit).first() }

    val state: LiveData<GameSaveFilterActionUIStateLiveData.UIState> =
        gameSaveFilterActionUIStateLiveData.status.switchMap { action ->
            when (action) {
                GameSaveFilterActionUIStateLiveData.Action.Status.Saved,
                GameSaveFilterActionUIStateLiveData.Action.Status.Load -> {
                    getGamesUseCase(
                        GetGamesUseCase.GetGamesParams(
                            pagingConfig = PagingConfig(
                                pageSize = NUMBER_PER_PAGE
                            )
                        )
                    ).cachedIn(viewModelScope).map { data ->
                        GameSaveFilterActionUIStateLiveData.UIState.Success(data)
                    }.asLiveData(Dispatchers.Main)
                }
            }
        }

    fun setDefaultFilter(filter: (Int) -> Unit) {
        filter(when(restoredFilter) {
            GameFilters.SWITCH_ONLY -> R.id.chip_all
            GameFilters.SWITCH_COMING_SOON -> R.id.chip_news
            GameFilters.SWITCH_SALES -> R.id.chip_sales
            GameFilters.SWItCH_NEWS_RELEASE -> R.id.chip_new_releases
        })
    }
}