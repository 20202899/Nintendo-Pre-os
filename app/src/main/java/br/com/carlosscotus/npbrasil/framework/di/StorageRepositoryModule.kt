package br.com.carlosscotus.npbrasil.framework.di

import br.com.carlosscotus.core.data.repository.StorageLocalDataSource
import br.com.carlosscotus.core.data.repository.StorageRepository
import br.com.carlosscotus.npbrasil.framework.local.DataStorePreferencesDataSource
import br.com.carlosscotus.npbrasil.framework.repository.StorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageRepositoryModule {

    @Binds
    fun bindStorageDataSource(
        storageLocalDataSource: DataStorePreferencesDataSource
    ): StorageLocalDataSource

    @Binds
    @Singleton
    fun bindStorageRepository(
        storageRepository: StorageRepositoryImpl
    ): StorageRepository
}