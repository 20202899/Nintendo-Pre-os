package br.com.carlosscotus.npbrasil.framework.di

import br.com.carlosscotus.core.domain.model.Game
import br.com.carlosscotus.core.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetGamesUseCase(
        getGamesUseCase: GetGamesUseCaseImpl
    ): GetGamesUseCase<Game>

    @Binds
    fun bindGetGameDetailUseCase(
        getGameDetailUseCase: GetGameDetailUseCaseImpl
    ): GetGameDetailUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCaseImpl): AddFavoriteUseCase
}