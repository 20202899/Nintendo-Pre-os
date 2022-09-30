package br.com.carlosscotus.npbrasil.presentation.detail

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.AddFavoriteUseCase
import br.com.carlosscotus.core.usecase.GetGameDetailUseCase
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getGameDetailUseCase: GetGameDetailUseCase
) : ViewModel() {

    private val _favoriteUIState: MutableLiveData<FavoriteUIState> = MutableLiveData()
    val favoriteUIState: LiveData<FavoriteUIState>
        get() = _favoriteUIState

    private val _detailUIState: MutableLiveData<DetailUIState> = MutableLiveData()
    val detailUIState: LiveData<DetailUIState>
        get() = _detailUIState

    fun addFavorite(gameDetailArg: GameDetailArg) = viewModelScope.launch {
//        addFavoriteUseCase(
//            AddFavoriteUseCase.Params(
//                gameDetailArg.id,
//                gameDetailArg.title,
//                gameDetailArg.imageUrl,
//                gameDetailArg.price,
//                gameDetailArg.featured,
//                gameDetailArg.priceDiscount,
//                gameDetailArg.discountPercentage,
//                gameDetailArg.hasDiscount
//            )
//        ).watchStatus(
//            loading = {},
//            success = {
//                _favoriteUIState.value = FavoriteUIState.FavoriteIcon(R.drawable.ic_round_favorite_24)
//            },
//            error = {}
//        )
    }

    fun loadGameDetail(productId: String) = viewModelScope.launch {
        getGameDetailUseCase(productId).watchStatus(
            loading = {
                _detailUIState.value = DetailUIState.Loading
            },
            success = { game ->
                _detailUIState.value = DetailUIState.Success(game)
            },
            error = { throwable ->
                _detailUIState.value = DetailUIState.Error(throwable)
            }
        )
    }

    sealed class DetailUIState {
        object Loading : DetailUIState()
        data class Success(val data: Game) : DetailUIState()
        data class Error(val throwable: Throwable) : DetailUIState()
    }

    sealed class FavoriteUIState {
        object Loading : FavoriteUIState()
        data class FavoriteIcon(@DrawableRes val res: Int) : FavoriteUIState()
    }
}