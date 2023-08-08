package pe.ralvaro.movietek.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import pe.ralvaro.movietek.utils.Result
import pe.ralvaro.movietek.utils.Result.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Success(it)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Error(e)
        }
    }

    protected abstract suspend fun execute(parameters: P): R
}

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e ->
            Timber.e(e, "Error executing flow use case")
            emit(Error(Exception(e)))
        }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}
