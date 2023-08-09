package pe.ralvaro.movietek.domain.home

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.movietek.data.database.MovieEntity
import pe.ralvaro.movietek.data.database.toDomainModel
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.di.IoDispatcher
import pe.ralvaro.movietek.domain.FlowUseCase
import pe.ralvaro.movietek.utils.Result
import javax.inject.Inject

class MoviePaginationAccessUseCase @Inject constructor(
    private val pager : Pager<Int, MovieEntity>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): FlowUseCase<Unit, PagingData<Movie>>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<PagingData<Movie>>> {
        return pager
            .flow
            .map {
                Result.Success(it.map { movieEntity ->
                    movieEntity.toDomainModel()
                })
            }
    }
}