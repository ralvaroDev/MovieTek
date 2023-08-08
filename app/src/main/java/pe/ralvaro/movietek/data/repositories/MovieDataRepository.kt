package pe.ralvaro.movietek.data.repositories

import pe.ralvaro.movietek.data.models.UpcomingMovies
import pe.ralvaro.movietek.data.network.models.toDomainModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Single point of access to movie data for the presentation layer
 * This ensure one type of source data, remote or local.
 * We try to prioritize the remote data, but if there is no internet connection, we use the local data
 */
@Singleton
class MovieDataRepository @Inject constructor(
    @Named("remoteDataSource") private val remoteMovieSource: MovieDataSource,
    @Named("localDataSource") private val localMovieSource: MovieDataSource
) {

    // Represents the movie cache data
    private var movieDataCache: UpcomingMovies? = null

    // This to know where the data comes from
    var dataSource: DataTagSource? = null
        private set

    // Key to prevent race conditions
    private val loadMovieDataLocker = Any()

    // Call this func to request upcoming movies from server
    suspend fun downloadMoviesFromServer(page: Int) {
        val movies = try {
            remoteMovieSource.getUpcomingMovies(page)
        } catch (e: Exception) {
            Timber.e(e, "Connection failed, no data from remote")
            null
        }

        if (movies == null) {
            val e = Exception("No data from remote")
            throw e
        }

        /*
        * This ensure that only one sub process access to this code, to avoid inconsistent data
        * because of concurrency
        * */
        synchronized(loadMovieDataLocker) {
            // Network data success
            dataSource = DataTagSource.NETWORK
            movieDataCache = movies.toDomainModel()
            updateDatabase(movieDataCache!!)
        }
    }

    private fun updateDatabase(movieDataCache: UpcomingMovies) {
        Timber.d("Updating database")
    }

    fun getOfflineData(): UpcomingMovies {
        synchronized(loadMovieDataLocker) {
            val cacheData = movieDataCache ?: loadDataFromRoom()
            movieDataCache = cacheData
            return cacheData
        }
    }

    private fun loadDataFromRoom(): UpcomingMovies {
        dataSource = DataTagSource.LOCAL
        return UpcomingMovies(
            page = 3401, movieList = listOf(), totalPages = 9509, totalResults = 6626
        )
    }

}

enum class DataTagSource {
    NETWORK, LOCAL
}