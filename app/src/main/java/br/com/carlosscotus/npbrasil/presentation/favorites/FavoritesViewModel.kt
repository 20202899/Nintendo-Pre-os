package br.com.carlosscotus.npbrasil.presentation.favorites

import androidx.lifecycle.*
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UIState>
        get() = action.switchMap { action ->
            liveData(Dispatchers.IO) {
                when (action) {
                    Action.GetFavorites -> {
                        getFavoritesUseCase()
                            .catch {
                                // TODO: tratar erro
                            }
                            .collect { games ->
                                val favorites = games.map { it.toFavoriteUI() }
                                emit(if (favorites.isEmpty()) {
                                    UIState.Empty
                                } else UIState.Success(favorites))
                            }
                    }
                }
            }
        }

    fun getFavorites() {
        action.value = Action.GetFavorites
    }

    sealed class Action {
        object GetFavorites : Action()
    }

    sealed class UIState {
        data class Success(val list: List<FavoriteUI>) : UIState()
        object Empty : UIState()
    }
}