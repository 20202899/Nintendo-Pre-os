package br.com.carlosscotus.npbrasil.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.GetGameDetailUseCase
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GameDetailActionUIStateLiveData(
    private val gameGameDetailUseCase: GetGameDetailUseCase,
    private val dispatcher: CoroutineDispatcher
) {

    private var _game: Game? = null
    val game: Game
        get() = _game!!

    private val action: MutableLiveData<Action> = MutableLiveData()
    val state: LiveData<UIState>
        get() = action.switchMap { action ->
            liveData(dispatcher) {
                when(action) {
                    is Action.Load -> {
                        gameGameDetailUseCase(action.productId)
                            .watchStatus(
                                loading = {
                                    emit(UIState.Loading)
                                },
                                success = { game ->
                                    _game = game
                                    emit(UIState.Success(game))
                                },
                                error = { throwable ->
                                    emit(UIState.Error(throwable))
                                }
                            )
                    }
                }
            }
        }

    fun load(productId: String) {
        action.value = Action.Load(productId)
    }

    sealed class Action {
        data class Load(val productId: String) : Action()
    }

    sealed class UIState {
        object Loading : UIState()
        data class Success(val data: Game) : UIState()
        data class Error(val throwable: Throwable) : UIState()
    }
}