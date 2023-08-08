package pe.ralvaro.movietek.domain.login

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pe.ralvaro.movietek.data.repositories.CredentialsDataRepository
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.FlowUseCase
import pe.ralvaro.movietek.ui.InitialView
import pe.ralvaro.movietek.ui.login.LoginStatus
import pe.ralvaro.movietek.utils.Result
import timber.log.Timber
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val credentialsDataRepository: CredentialsDataRepository,
    private val preferenceStoreRepository: PreferenceStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Pair<String, String>, LoginStatus>(dispatcher) {
    override fun execute(parameters: Pair<String, String>): Flow<Result<LoginStatus>> {
        val (email, password) = parameters
        return flow {
            val responseServer = credentialsDataRepository.makeLoginInServer(email, password)
            responseServer.let {
                when(it) {
                    is Result.Error -> {
                        Timber.e(it.exception)
                        emit(Result.Error(it.exception))
                    }
                    else -> {
                        preferenceStoreRepository.updateInitialView(InitialView.Main)
                        emit(Result.Success(LoginStatus.Success))
                    }
                }
            }
        }
    }
}