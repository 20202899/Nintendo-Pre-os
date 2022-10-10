package br.com.carlosscotus.npbrasil.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.lifecycle.*
import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.AddFavoriteUseCase
import br.com.carlosscotus.core.usecase.GetGameDetailUseCase
import br.com.carlosscotus.core.usecase.HasFavoriteUseCase
import br.com.carlosscotus.core.usecase.RemoveFavoriteUseCase
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.framework.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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