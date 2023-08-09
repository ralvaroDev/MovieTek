package pe.ralvaro.movietek.data.repositories

import kotlinx.coroutines.flow.map
import pe.ralvaro.movietek.data.database.MovieDao
import pe.ralvaro.movietek.data.database.toDomainModel
import javax.inject.Inject

/**
 * Single point of access to movies
 */
open class MoviesRepository @Inject constructor(
    private val dao: MovieDao
) {

    open fun getMovie(idMovie: Int) = dao.getMovieById(id = idMovie).map {
        it.toDomainModel()
    }

    suspend fun clearSession() = dao.deleteAll()

}