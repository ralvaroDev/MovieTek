package pe.ralvaro.movietek.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import pe.ralvaro.movietek.data.repositories.MoviesRepository
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.UseCase
import pe.ralvaro.movietek.ui.InitialView
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository,
    private val moviesRepository: MoviesRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): UseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(parameters: Unit) {
        moviesRepository.clearSession()
        preferenceStoreRepository.updateInitialView(InitialView.Login)
    }
}