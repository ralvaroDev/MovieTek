package pe.ralvaro.movietek.data.network.models

import com.google.gson.annotations.SerializedName
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.data.models.UpcomingMovies

data class NetUpcomingContainer(
    @SerializedName("dates") val dates: NetDates,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movieList: List<NetMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class NetDates(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
)

data class NetMovie(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

fun NetUpcomingContainer.toDomainModel(): UpcomingMovies {
    return UpcomingMovies(
        page = page,
        movieList = movieList.toDomainModel(),
        totalPages = totalPages,
        totalResults = totalResults
    )
}

private fun List<NetMovie>.toDomainModel() : List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage,
            overview = it.overview
        )
    }
}
