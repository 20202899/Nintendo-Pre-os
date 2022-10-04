package br.com.carlosscotus.npbrasil.framework.extensions

import br.com.carlosscotus.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<ResultStatus<T>>.watchStatus(
    loading: suspend () -> Unit = {},
    success: suspend (data: T)  -> Unit,
    error: suspend (throwable: Throwable) -> Unit
) {
    collect { status ->
        when(status) {
            ResultStatus.Loading -> loading.invoke()
            is ResultStatus.Success<*> -> success.invoke(status.data as T)
            is ResultStatus.Error -> error.invoke(status.throwable)
        }
    }
}