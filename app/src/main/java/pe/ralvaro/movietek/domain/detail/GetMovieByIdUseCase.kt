package pe.ralvaro.movietek.domain.detail

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.data.repositories.MoviesRepository
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.FlowUseCase
import pe.ralvaro.movietek.utils.Result
import pe.ralvaro.movietek.utils.Result.Success
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Int, Movie>(dispatcher) {
    override fun execute(parameters: Int): Flow<Result<Movie>> {
        return repository.getMovie(parameters).map {
            Success(it)
        }
    }
}