package pe.ralvaro.movietek.domain.launcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.FlowUseCase
import pe.ralvaro.movietek.ui.InitialView
import pe.ralvaro.movietek.utils.Result
import javax.inject.Inject

class InitialViewUseCase @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, InitialView>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<InitialView>> {
        return preferenceStoreRepository.initialView.map {
            Result.Success(it)
        }
    }

}