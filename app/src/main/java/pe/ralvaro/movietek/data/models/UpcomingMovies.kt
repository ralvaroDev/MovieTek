package pe.ralvaro.movietek.data.models

data class Movie(
    val uuid: Int,
    val id: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String
)