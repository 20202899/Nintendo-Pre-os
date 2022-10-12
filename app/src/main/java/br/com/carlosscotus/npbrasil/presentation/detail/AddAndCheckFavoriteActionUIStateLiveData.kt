package br.com.carlosscotus.npbrasil.presentation.detail

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import br.com.carlosscotus.core.usecase.AddFavoriteUseCase
import br.com.carlosscotus.core.usecase.HasFavoriteUseCase
import br.com.carlosscotus.core.usecase.RemoveFavoriteUseCase
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import kotlinx.coroutines.CoroutineDispatcher

class AddAndCheckFavoriteActionUIStateLiveData(
    private val hasFavoriteUseCase: HasFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    
    private var isFavorite = false

    private val action: MutableLiveData<Action> = MutableLiveData()
    val state: LiveData<State>
        get() = action.switchMap { action ->
            liveData(coroutineDispatcher) {
                when (action) {
                    is Action.CheckFavorite -> {
                        hasFavoriteUseCase(action.gameId).watchStatus(
                            success = { isAdded ->
                                var icon = R.drawable.ic_round_favorite_border_24
                                if(isAdded) {
                                    icon = R.drawable.ic_round_favorite_24
                                }
                                isFavorite = isAdded
                                emit(State.IconFavorite(icon))
                            },
                            error = {
                                // TODO: implementar erro
                            }
                        )
                    }
                    is Action.RemoveFavorite -> {
                        action.game.let {
                            removeFavoriteUseCase(
                                RemoveFavoriteUseCase.Params(
                                    it.id,
                                    it.title,
                                    it.imageUrl,
                                    it.price,
                                    it.featured,
                                    it.priceDiscount,
                                    it.discountPercentage,
                                    it.hasDiscount,
                                    it.description,
                                    it.productId,
                                    it.releaseDate
                                )
                            ).watchStatus(
                                success = {
                                    isFavorite = !isFavorite
                                    emit(State.IconFavorite(R.drawable.ic_round_favorite_border_24))
                                },
                                error = {
                                    // TODO: implementar erro
                                }
                            )
                        }
                    }
                    is Action.AddFavorite -> {
                        action.game.let {
                            addFavoriteUseCase(
                                AddFavoriteUseCase.Params(
                                    it.id,
                                    it.title,
                                    it.imageUrl,
                                    it.price,
                                    it.featured,
                                    it.priceDiscount,
                                    it.discountPercentage,
                                    it.hasDiscount,
                                    it.description,
                                    it.productId,
                                    it.releaseDate
                                )
                            ).watchStatus(
                                success = {
                                    isFavorite = !isFavorite
                                    emit(State.IconFavorite(R.drawable.ic_round_favorite_24))
                                },
                                error = {
                                    // TODO: implementar erro
                                }
                            )
                        }
                    }
                }
            }
        }

    fun addFavorite(game: GameDetailArg) {
        if (isFavorite) {
            action.value = Action.RemoveFavorite(game)
        } else {
            action.value = Action.AddFavorite(game)
        }
    }

    fun hasFavorite(id: String) {
        action.value = Action.CheckFavorite(id)
    }

    sealed class Action {
        data class CheckFavorite(val gameId: String) : Action()
        data class AddFavorite(val game: GameDetailArg) : Action()
        data class RemoveFavorite(val game: GameDetailArg) : Action()
    }

    sealed class State {
        data class IconFavorite(@DrawableRes val icon: Int) : State()
    }
}