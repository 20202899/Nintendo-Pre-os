package br.com.carlosscotus.npbrasil.presentation.detail

import androidx.lifecycle.ViewModel
import br.com.carlosscotus.core.usecase.AddFavoriteUseCase
import br.com.carlosscotus.core.usecase.GetGameDetailUseCase
import br.com.carlosscotus.core.usecase.HasFavoriteUseCase
import br.com.carlosscotus.core.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    hasFavoriteUseCase: HasFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    removeFavoriteUseCase: RemoveFavoriteUseCase,
    getGameDetailUseCase: GetGameDetailUseCase
) : ViewModel() {

    val gameDetailActionUIState = GameDetailActionUIStateLiveData(
        getGameDetailUseCase,
        Dispatchers.IO
    )

    val addAndCheckFavoriteActionUIStateLiveData = AddAndCheckFavoriteActionUIStateLiveData(
        hasFavoriteUseCase,
        addFavoriteUseCase,
        removeFavoriteUseCase,
        Dispatchers.IO
    )
}