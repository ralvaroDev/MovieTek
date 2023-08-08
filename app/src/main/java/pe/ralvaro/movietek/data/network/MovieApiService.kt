package pe.ralvaro.movietek.data.network

import pe.ralvaro.movietek.data.network.models.NetUpcomingContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("upcoming/")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ) : NetUpcomingContainer


}