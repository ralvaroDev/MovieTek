package pe.ralvaro.movietek.data.repositories

import pe.ralvaro.movietek.data.network.models.NetUpcomingContainer
import javax.inject.Inject

class LocalMovieSource @Inject constructor(

) : MovieDataSource {

    override suspend fun getUpcomingMovies(page: Int): NetUpcomingContainer? {
        return null
    }

}