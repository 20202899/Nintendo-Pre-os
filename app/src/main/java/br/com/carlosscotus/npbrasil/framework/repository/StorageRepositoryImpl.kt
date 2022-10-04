package br.com.carlosscotus.npbrasil.framework.repository

import br.com.carlosscotus.core.data.repository.StorageLocalDataSource
import br.com.carlosscotus.core.data.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageLocalDataSource: StorageLocalDataSource
) : StorageRepository {
    override val sorting: Flow<String>
        get() = storageLocalDataSource.sorting

    override suspend fun saveSorting(sorting: String) {
        return storageLocalDataSource.saveSorting(sorting)
    }
}