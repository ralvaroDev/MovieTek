package pe.ralvaro.movietek.data.models

data class UpcomingMovies(
    val page: Int,
    val movieList: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val overview: String
)