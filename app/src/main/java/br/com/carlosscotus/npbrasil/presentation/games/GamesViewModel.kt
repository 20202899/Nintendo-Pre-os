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
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import br.com.carlosscotus.npbrasil.framework.paging.NUMBER_PER_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
}