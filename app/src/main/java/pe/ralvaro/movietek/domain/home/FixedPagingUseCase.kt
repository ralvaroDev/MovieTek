package pe.ralvaro.movietek.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.FlowUseCase
import pe.ralvaro.movietek.utils.Result
import javax.inject.Inject

class FixedPagingUseCase @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): FlowUseCase<Unit, Boolean>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<Boolean>> {
        return preferenceStoreRepository.isPagingFixed.map {
            Result.Success(it)
        }
    }
}