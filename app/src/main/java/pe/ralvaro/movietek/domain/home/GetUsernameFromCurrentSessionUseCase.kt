package pe.ralvaro.movietek.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pe.ralvaro.movietek.data.repositories.CredentialsDataRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.FlowUseCase
import pe.ralvaro.movietek.utils.Result
import javax.inject.Inject

class GetUsernameFromCurrentSessionUseCase @Inject constructor(
    private val credentialsDataRepository: CredentialsDataRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, String>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<String>> {
        return flow {
            emit(Result.Success(credentialsDataRepository.getUsername()))
        }
    }
}