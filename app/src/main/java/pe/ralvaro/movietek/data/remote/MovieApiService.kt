package pe.ralvaro.movietek.data.remote

import pe.ralvaro.movietek.data.remote.models.NetUpcomingContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ) : NetUpcomingContainer


}