package pe.ralvaro.movietek.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.UseCase
import javax.inject.Inject

class FixedPagingActionUseCase @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(parameters: Unit) {
        preferenceStoreRepository.updateIsPagingFixed(true)
    }
}