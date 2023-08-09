package pe.ralvaro.movietek.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import pe.ralvaro.movietek.data.models.Movie

// Let's declare [indices] to ensure unique movies and don't depend of it as primary key
// this to avoid unordered saved data. Without that the data is saved ascending by id, and the
// id provided by the server is randomize, this cause reordering the data in not desired way.
@Entity(
    tableName = "movie_table",
    indices = [Index(value = ["id_movie"], unique = true)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val uuid: Int = 0,
    @ColumnInfo("id_movie") val idMovie: Int,
    val title: String,
    @ColumnInfo("poster_path") val posterPath: String?,
    @ColumnInfo("vote_average") val voteAverage: Double,
    val overview: String,
    @ColumnInfo("release_date") val releaseDate: String
)

fun MovieEntity.toDomainModel(): Movie {
    return Movie(
        uuid = uuid,
        id = idMovie,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        overview = overview,
        releaseDate = releaseDate
    )
}