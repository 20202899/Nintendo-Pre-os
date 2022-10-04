package br.com.carlosscotus.npbrasil.presentation.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.paging.PagingData
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.SaveGamesFilterUseCase
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import kotlinx.coroutines.CoroutineDispatcher

class GameSaveFilterActionUIStateLiveData(
    private val saveGamesFilterUseCase: SaveGamesFilterUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    private val action = MutableLiveData<Action>()
    val status: LiveData<Action.Status>
        get() = action.switchMap { action ->
            liveData(coroutineDispatcher) {
                when(action) {
                    is Action.SaveFilter -> {
                        saveGamesFilterUseCase(action.filter).watchStatus(
                            loading = {},
                            success = {
                                emit(Action.Status.Saved)
                            },
                            error = {}
                        )
                    }
                }
            }
        }

    fun saveFilter(filter: GameFilters) {
        action.value = Action.SaveFilter(filter)
    }

    sealed class UIState {
        class Success(val data: PagingData<Game>) : UIState()
    }

    sealed class Action {
        class SaveFilter(val filter: GameFilters) : Action()

        sealed class Status {
            object Saved : Status()
        }
    }
}