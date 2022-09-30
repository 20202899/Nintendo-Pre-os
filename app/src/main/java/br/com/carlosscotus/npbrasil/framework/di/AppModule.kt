package br.com.carlosscotus.npbrasil.framework.di

import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoaderImpl: ImageLoaderImpl): ImageLoader
}