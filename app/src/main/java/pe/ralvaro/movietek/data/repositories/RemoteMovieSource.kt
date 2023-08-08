package pe.ralvaro.movietek.data.repositories

import pe.ralvaro.movietek.data.network.models.NetUpcomingContainer
import pe.ralvaro.movietek.data.network.MovieApiService
import pe.ralvaro.movietek.utils.NetworkUtils
import timber.log.Timber
import javax.inject.Inject

interface MovieDataSource {
    suspend fun getUpcomingMovies(page: Int): NetUpcomingContainer?
}

class RemoteMovieSource @Inject constructor(
    private val movieApiService: MovieApiService,
    private val networkUtils: NetworkUtils
) : MovieDataSource {

    override suspend fun getUpcomingMovies(page: Int):  NetUpcomingContainer?{
        return if (!networkUtils.hasNetworkConnection()) {
            Timber.d("No internet connection")
            null
        } else getRemoteMovies(page)
    }

    private suspend fun getRemoteMovies(page: Int): NetUpcomingContainer? {
        return movieApiService.getUpcomingMovies(page)
    }

}